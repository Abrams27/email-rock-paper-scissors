#!/usr/bin/env bash

CONTAINER=$(docker ps --filter="name=matchmaker" -q)

docker exec -it $CONTAINER sh -c \
    "pip install ptpython; echo 'exec(open(\"interactive.py\").read())' > .tmp; ptpython -i .tmp"
