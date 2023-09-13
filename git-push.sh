#!/bin/bash

gh repo create micropets-dogs-springboot --public
git init
git remote add origin git@github.com:bmoussaud/micropets-dogs-springboot.git
git branch -M main
git add -A  .
git commit -m "initial commit"
git push -u origin main
open https://github.com/bmoussaud/micropets-dogs-springboot