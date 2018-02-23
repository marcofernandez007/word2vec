#!/bin/bash

dir=$1
out=$2

for f in "$dir"/*; do

  pdftotext "$f" - | tr . '\n' | perl -pe 's/[^[:ascii:]]//g' | awk 'length($0)>10' | sed 's/,/ ,/g' | sed 's/?/ ?/g' | sed 's/!/ !/g' | sed 's/“/" /g' | sed 's/”/ "/g' | sed '/^\s*$/d' | sed "s/'/ '/g" | sed 's/(/( /g' | sed 's/)/ )/g' | sed '/^\s*$/d' >>"$out"

done
