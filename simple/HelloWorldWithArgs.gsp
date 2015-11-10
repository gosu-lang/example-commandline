uses gw.lang.Gosu

var args = Gosu.RawArgs

print("hello, I found these arguments:")
args.each( \ arg -> print('\t' + arg) )
