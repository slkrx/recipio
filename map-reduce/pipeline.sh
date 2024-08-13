#!/bin/bash
set -Eeuo pipefail

rm -rf output1

hadoop \
  jar hadoop-streaming-2.7.2.jar \
  -input input \
  -output output1 \
  -mapper ./map0.py \
  -reducer ./reduce0.py

rm -rf output2

hadoop \
  jar hadoop-streaming-2.7.2.jar \
  -input output1 \
  -output output2 \
  -mapper ./map1.py \
  -reducer ./reduce1.py

rm -rf output3

hadoop \
  jar hadoop-streaming-2.7.2.jar \
  -input output2 \
  -output output3 \
  -mapper ./map2.py \
  -reducer ./reduce2.py

rm -rf output4

hadoop \
  jar hadoop-streaming-2.7.2.jar \
  -input output3 \
  -output output4 \
  -mapper ./map3.py \
  -reducer ./reduce3.py

rm -rf output5

hadoop \
  jar hadoop-streaming-2.7.2.jar \
  -input output4 \
  -output output5 \
  -mapper ./map4.py \
  -reducer ./reduce4.py

cat output5/* | sort >inverted_index.txt
