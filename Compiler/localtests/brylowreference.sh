#!/bin/bash
SCRIPTDIR="$(echo ~brylow/cosc4400/Projects)"
OUT="./main"
USAGE="Usage: ./brylowreference.sh [-o OUT_PATH] [-s starting_phase] [-f final_phase] inputfile.java"

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
EXT="S"

#parse options of script
while getopts :o:s:l: opt
do      case $opt in
        o)
            echo "-o was triggered. Parameter: $OPTARG" >&2
            OUT=$OPTARG
            ;;
        s)
            echo "-s was triggered. Parameter: $OPTARG" >&2
            STARTPHASE=$OPTARG
            ;;
        l)
            echo "-l was trigged. Parameter: $OPTARG" >&2
            FINALPHASE=$OPTARG
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
        EXT="ast"
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
        EXT="ast.types"
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
        EXT="tree"
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
        EXT="instr"
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
COMMAND="$COMMAND > $OUT.$EXT"

#Run command
eval $COMMAND
echo "Output to $OUT.$EXT"
