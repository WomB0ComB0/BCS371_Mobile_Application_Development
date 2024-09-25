#!/bin/bash

for i in {1..14}; do
    mkdir week-$i
    mkdir week-$i/classwork
    mkdir week-$i/homework
    touch week-$i/classwork/.gitkeep
    touch week-$i/homework/.gitkeep
done