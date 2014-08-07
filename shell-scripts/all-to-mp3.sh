#!/bin/bash
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
in=$(zenity --file-selection --directory)
cd "$in"
no=$(ls -1 $in | wc -l)
FILES="$in/"
oo="0"
for file in "$FILES"*
do
filename=$(basename "$file")
extension=${filename##*.}
name=${filename%.*}
per=`expr $oo \* 100 / $no`
ffmpeg -i "$file" -vn -ar 44100 -ac 2 -ab 192 -f mp3 "$name".mp3 | zenity --progress --auto-close --percentage=$per --text="Converting $file..." --title="Make it mp3!"
oo=`expr $oo + 1`
done
zenity --info --text "encoding done"
