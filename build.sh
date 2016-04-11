#!/usr/bin/env bash
# Execute this in root directory to build gh-pages

git checkout master
git branch -D gh-pages
git checkout --orphan gh-pages

lein clean
lein cljsbuild once prod
sass --update sass:resources/public/css/out

mv resources/public ./public

ls -a | awk '{if(NR>2)print}'| grep -v ".git*" | grep -v public | xargs rm

cp -r ./public/. ./
rm -r public

git add . && git commit -m 'Meaningless commit for gh-pages'
