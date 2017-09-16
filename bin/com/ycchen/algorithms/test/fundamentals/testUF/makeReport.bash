#!/bin/bash
function writeReport {
	file=$1
	echo $file >>report.txt
	grep "Time" $file >>report.txt
	echo "" >>report.txt
}
export -f writeReport
rm ./report.txt 
find *_*txt | xargs -I {} -n 1 bash -c 'writeReport $@' _ {}
cat ./report.txt
