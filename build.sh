#!/usr/bin/env zsh

# Create fresh orphan branch
git checkout master
git branch -D gh-pages
git checkout --orphan gh-pages

# Build
lein clean
lein cljsbuild once prod
sass --update sass:resources/public/css/out

# Move resources to top level
mv resources/public ./public

# Delete everything in top level except git related and resource file
ls -a | awk '{if(NR>2)print}'| grep -v ".git*" | grep -v public | xargs rm -r

# Move resource contents to top level
cp -r ./public/. ./
# Delete resource folder
rm -r public

# Commit to git and push
git add .
git commit -m 'Meaningless commit for gh-pages'
git config branch.gh-pages.pushRemote origin
git push -v --force-with-lease origin gh-pages:refs/heads/gh-pages

# Remove this script
rm -- "$0"
