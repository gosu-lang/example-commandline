uses gw.lang.cli.CommandLineAccess

var args = CommandLineAccess.RawArgs

print("hello, I found these arguments:")
args.each( \ arg -> print('\t' + arg) )
