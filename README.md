# modest-let-flow

Provides alternate versions of `let-flow` & `let-flow'` that blocks
only on deferreds defined in bindings.

As an example the following snippet would block on `x` with manifold's
`let-flow` but not with the alternative this library provides:

    (let [x (d/future 2)]
      (let-flow [y (d/future 3)]
        (+ @x y)))


## See Also

[Manifold](https://github.com/ztellman/manifold)


## License

Copyright © 2015 Atamert Ölçgen

Distributed under the MIT License.
