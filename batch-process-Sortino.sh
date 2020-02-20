#! /bin/sh 
for f in "$1"/*; do
  if [ -f "$f" ]; then
    NAME="$(basename "${f}")"
    WORD="$(wc -w "${f}" | cut -d' ' -f1)"
    SIZE="$(du -sh "${f}" | cut -f1)"
    echo "$NAME"
    echo "$WORD"
    echo "$SIZE"
  fi
done

