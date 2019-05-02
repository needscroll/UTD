#!/bin/bash

mkdir subdir
echo "creating sub directory"
rsync -a --exclude='subdir' * subdir
echo "putting files inside"
rm -rf subdir
echo "deleting sub directory"
echo "task is done"
