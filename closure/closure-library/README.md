# Closure Library

This is Medium's fork of the
[Google Closure Library](https://github.com/google/closure-library).

We try to keep it reasonably up to date, but only after testing that it is
compatible with our products. There may occasionally be changes introduced to
work around temporary issues or to try out fixes in preparation of upstream
patches.

The package number reflects the date that we last merged the fork. We rebase
our changes on top of origin/master, so that our modifications are always on top of
the closure-library master changes.

## NPM Info

The repository contains a lot of test files and demos, as such we include a
postinstall script that removes these files from the `node_modules` directory
when installed via NPM.

## Contributing

This project isn't intended for external contribution, we suggest instead you
[send patches](https://code.google.com/p/closure-library/wiki/Contributors)
directly.

## Original Readme

Closure Library is a powerful, low level JavaScript library designed for
building complex and scalable web applications. It is used by many major Google
web applications, such as Gmail and Google Docs.

For more information about Closure Library, visit:
http://code.google.com/closure/library
