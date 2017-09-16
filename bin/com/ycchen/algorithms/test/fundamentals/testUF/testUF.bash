#!/bin/bash
root=`readlink -f ../../../../../../../`
tiny="$root/resources/tinyUF.txt"
med="$root/resources/mediumUF.txt"
large="$root/resources/largeUF.txt"
output="$root/test/com/ycchen/algorithms/test/fundamentals/testUF"

cd $root/bin
java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.QuickFindUF <$tiny >$output/QuickFind_T.txt
java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.QuickUnionUF <$tiny >$output/QuickUnionUF_T.txt
java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.WeightedQuickUnionUF <$tiny >$output/WeightedQuickUnionUF_T.txt
java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.PathCompressionUF <$tiny >$output/PathCompressionUF_T.txt

java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.QuickFindUF <$med >$output/QuickFind_M.txt
java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.QuickUnionUF <$med >$output/QuickUnionUF_M.txt
java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.WeightedQuickUnionUF <$med >$output/WeightedQuickUnionUF_M.txt
java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.PathCompressionUF <$med >$output/PathCompressionUF_M.txt

java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.QuickFindUF <$large >$output/QuickFind_L.txt
java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.QuickUnionUF <$large >$output/QuickUnionUF_L.txt
java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.WeightedQuickUnionUF <$large >$output/WeightedQuickUnionUF_L.txt
java -cp .:$root/lib/* com.ycchen.algorithms.fundamentals.PathCompressionUF <$large >$output/PathCompressionUF_L.txt

