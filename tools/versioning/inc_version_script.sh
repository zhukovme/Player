#!/bin/bash

FILE="tools/versioning/version.properties"
IFS=$'\n'

increment() {
  CONTENT=$(cat ./$FILE)
  > $FILE
  for line in $CONTENT; do
    if [[ $line =~ ($1 = [0-9]+)$ ]]; then
      echo $line | awk '{printf "%s = %d\n", $1, $3 + 1}' >> $FILE
    else
      echo $line >> $FILE
    fi
  done
  CONTENT=$(cat ./$FILE)
}

increment "patch"
# new_version="${CONTENT//$'\n'/ }"
