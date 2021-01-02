#!/bin/bash
rm settings.gradle
echo "include ':kommon'" >> settings.gradle
echo "include ':kommonviews'" >> settings.gradle
echo "include ':kommonadapter'" >> settings.gradle
echo "include ':kommonadapter-viewbinding'" >> settings.gradle
