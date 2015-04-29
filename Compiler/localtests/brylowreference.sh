#!/bin/bash

SCRIPTDIR="$(echo ~brylow/cosc4400/Projects)"
USAGE="Usage: ./brylowreference.sh [-o OUT_PATH] [-s starting_phase] [-f final_phase] inputfile.java"

PHASES="parser checker translator assem allocator"

listcontains() {
    for word in $1; do
        [[ "$word" == "$2" ]] && return 0
    done
    return 1
}

STRINGLIST=""
printlist() {
    for word in $1; do
        STRINGLIST="$STRINGLIST '$word'"
    done
    echo "Available Phases: $STRINGLIST"
}

#Check for empty arguments and quit
if [[ -z "$1" ]]
    then
        echo $USAGE 
        exit 2
fi

#grab last argument in script
for INPUTFILE; do :; done

#Start phase is parse and end phase is reg allocator by default
STARTPHASE="parser"
FINALPHASE="alloc"
OUT=""

#parse options of script
while getopts :o:s:l: opt
do      case $opt in
        o)
            OUT=$OPTARG
            ;;
        s)
            STARTPHASE=$OPTARG
            if ! listcontains "$PHASES" "$OPTARG"; then 
                echo "ERROR:  Incorrect argument '$OPTARG'"
                printlist "$PHASES";
                exit 2;
            fi
            ;;
        l)
            FINALPHASE=$OPTARG
            if ! listcontains "$PHASES" "$OPTARG"; then
                echo "ERROR:  Incorrect argument '$OPTARG'"
                printlist "$PHASES";
                exit 2;
            fi
            ;;
        \?)
            echo "Invalid option: -$OPTARG" >&2
            echo $USAGE
            exit 1
            ;;
        :)
            echo "Option -$OPTARG requires an argument" >&2
            echo $USAGE
            exit 1
            ;;
    esac
done

#Initialize Phase and Command variables
CURPHASE=$STARTPHASE
COMMAND=""
SUBCOMMAND=""

#Parsing Phase
if [ "$CURPHASE" == "parser" ]; then
    SUBCOMMAND="$SCRIPTDIR/parser $INPUTFILE"
    if [ "$CURPHASE" == "$FINALPHASE" ]; then
        CURPHASE=""
        COMMAND="$COMMAND$SUBCOMMAND"
    else
        INPUTFILE="-"
        CURPHASE="checker"
        COMMAND="$COMMAND$SUBCOMMAND | "
    fi
fi

#Checker Phase
if [ "$CURPHASE" == "checker" ]; then
    SUBCOMMAND="$SCRIPTDIR/checker $INPUTFILE"
    if [ "$CURPHASE" == "$FINALPHASE" ]; then
        CURPHASE=""
        COMMAND=$COMMAND$SUBCOMMAND
    else
        INPUTFILE="-"
        CURPHASE="translator"
        COMMAND="$COMMAND$SUBCOMMAND | "
    fi
fi

#Translate Phase
if [ "$CURPHASE" == "translator" ]; then
    SUBCOMMAND="$SCRIPTDIR/translator $INPUTFILE"
    if [ "$CURPHASE" == "$FINALPHASE" ]; then
        CURPHASE=""
        COMMAND=$COMMAND$SUBCOMMAND
    else
        INPUTFILE="-"
        CURPHASE="assem"
        COMMAND="$COMMAND$SUBCOMMAND | "
    fi
fi

#Assembler Phase
if [ "$CURPHASE" == "assem" ]; then
    SUBCOMMAND="$SCRIPTDIR/assem $INPUTFILE"
    if [ "$CURPHASE" == "$FINALPHASE" ]; then
        CURPHASE=""
        COMMAND=$COMMAND$SUBCOMMAND
    else
        INPUTFILE="-"
        CURPHASE="allocator"
        COMMAND="$COMMAND$SUBCOMMAND | "
    fi

fi

#Allocator Phase
if [ "$CURPHASE" == "allocator" ]; then
    SUBCOMMAND="$SCRIPTDIR/allocator $INPUTFILE"
    COMMAND=$COMMAND$SUBCOMMAND
fi

#Append output file
if [[ ! -z "$OUT" ]]; then
    COMMAND="$COMMAND > $OUT"
    echo "Output to $OUT"
fi

#Run command
eval $COMMAND
exit 0
