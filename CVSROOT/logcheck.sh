#! /bin/sh

#
# capture log
#
TEMPFILE=/tmp/cvslog$$.tmp
cat > $TEMPFILE

#
# Invoke spackle
#
$CVSROOT/CVSROOT/log_accum.pl $USER "${1} ${2}" < $TEMPFILE

#
# Invoke cvsspam
#
$CVSROOT/CVSROOT/collect_diffs.rb --from $USER $@ < $TEMPFILE

#
# Invoke DamageControl
#
ruby /home/damagecontrol/current/bin/requestbuild --url http://localhost:4712/private/xmlrpc --projectname `echo ${1} | cut -d / -f 1`

#
# clean up
#
rm $TEMPFILE

#debug
echo -n "args "
while [ $# -ne 0 ]; do
  echo -n -$1
  shift
done
echo -