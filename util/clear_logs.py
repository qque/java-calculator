#!/usr/bin/env python3

import os

for file in os.listdir("logs"):
    path = os.path.join("logs", file)
    if os.path.isfile(path):
        os.remove(path)