#!/bin/bash
url="www.mscs.mu.edu/~brylow/cosc4400/Spring2013/Projects/Project3/AST/Absyn/"
url="$url$1"
url="$url.html"
wget -q $url
