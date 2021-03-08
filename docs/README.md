# Weapon regeX

[![Mutation testing badge](https://img.shields.io/endpoint?style=flat&url=https%3A%2F%2Fbadge-api.stryker-mutator.io%2Fgithub.com%2Fstryker-mutator%2Fweapon-regex%2Fmain)](https://dashboard.stryker-mutator.io/reports/github.com/stryker-mutator/weapon-regex/main)
[![Build Status](https://github.com/stryker-mutator/weapon-regex/workflows/Scala%20CI/badge.svg)](https://github.com/stryker-mutator/weapon-regex/actions?query=workflow%3AScala%20CI+branch%3Amain)
[![GitHub Pages](https://img.shields.io/static/v1?label=GitHub%20Pages&message=Try%20it!&color=blue&logo=github)](https://stryker-mutator.github.io/weapon-regex/)

<img src="images/WeaponRegeX_logo.svg" width="50%" alt="Weapon regeX Logo">

With Weapon regeX you can mutate regular expressions which can be used in mutation testing. The
generated regular expressions cover edge cases and typos. Weapon regeX is available for both
JavaScript and Scala. The JavaScript version of the library is generated from Scala using [Scala.js](https://www.scala-js.org/).

The current supported versions for Scala are: `2.12` and `2.13`.

# Getting started

## Scala

Add Weapon regeX to your `build.sbt`.

```scala
libraryDependencies += "io.stryker-mutator" %% "weapon-regex" % "0.3.0"
```

Mutate!

```scala mdoc
import weaponregex.WeaponRegeX
import scala.util.{Try, Success, Failure}

WeaponRegeX.mutate("^abc(d+|[xyz])$") match {
  case Success(mutants) => mutants map (_.pattern)
  case Failure(e)       => throw e
}
```

## JavaScript

Install Weapon regeX with `npm`.

```bash
npm install weapon-regex
```

Mutate!

```javascript
const wrx = require('weapon-regex');

let mutants = wrx.mutate('^abc(d+|[xyz])$');

mutants.forEach((mutant) => {
  console.log(mutant.pattern);
});
```

[![Try it!](https://img.shields.io/static/v1?label=RunKit&message=Try%20it!&color=F55FA6&logo=runkit)](https://npm.runkit.com/weapon-regex)

# API

## Scala

The `mutate` function has the following signature:

```scala mdoc
import weaponregex.model.mutation._
import weaponregex.mutator.BuiltinMutators
import weaponregex.parser.{ParserFlavor, ParserFlavorJVM}

def mutate(
  pattern: String,
  mutators: Seq[TokenMutator] = BuiltinMutators.all,
  mutationLevels: Seq[Int] = null,
  flavor: ParserFlavor = ParserFlavorJVM
): Try[Seq[Mutant]] = ???

WeaponRegeX.mutate _
```

With the `mutators` argument you can give a select list of mutators that should be used in
the mutation process. If omitted, all built-in mutators will be used. This list will be filtered
depending on the `mutationLevels` argument.

A list of `mutationLevels` can also be passed to the function. The mutators will be filtered
based on the levels in the list. If omitted, no filtering takes place.

The `flavor` argument allows setting the parser flavor that will be used to parse the pattern. 
Currently, we support a `ParserFlavorJVM` and `ParserFlavorJS`. By default in Scala the JVM flavor is used.

This function will return a `Success` with `Seq[Mutant]` if it can be parsed, or a `Failure` otherwise.

## JavaScript

The `mutate` function can be called with an options object to control which mutators and which parser flavor should be
used in the mutation process:

```js
const wrx = require('weapon-regex');

let mutants = wrx.mutate('^abc(d+|[xyz])$', {
  mutators: Array.from(wrx.mutators.values()),
  mutationLevels: [1, 2, 3],
  flavor: ParserFlavorJS
});
```

Both options can be omitted, and have the same functionality as the options described in the Scala
API section. By default in JS the JS parser flavor is used. You can get a map of mutators from the `mutators` attribute of the library. It is
a map from string (mutator name) to a mutator object.

This function will return a JavaScript Array of `Mutant` if it can be parsed, or throw an exception otherwise.

# Supported mutators

All the supported mutators and at which mutation level they appear are shown in the table below.

| Name                                                            | 1   | 2   | 3   |
| --------------------------------------------------------------- | --- | --- | --- |
| [BOLRemoval](#bolremoval)                                       | ✅  | ✅  | ✅  |
| [EOLRemoval](#eolremoval)                                       | ✅  | ✅  | ✅  |
| [BOL2BOI](#bol2boi)                                             |     | ✅  | ✅  |
| [EOL2EOI](#eol2eoi)                                             |     | ✅  | ✅  |
| [CharClassNegation](#charclassnegation)                         | ✅  |
| [CharClassChildRemoval](#charclasschildremoval)                 |     | ✅  | ✅  |
| [CharClassAnyChar](#charclassanychar)                           |     | ✅  | ✅  |
| [CharClassRangeModification](#charclassrangemodification)       |     |     | ✅  |
| [PredefCharClassNegation](#predefcharclassnegation)             | ✅  |
| [PredefCharClassNullification](#predefcharclassnullification)   |     | ✅  | ✅  |
| [PredefCharClassAnyChar](#predefcharclassanychar)               |     | ✅  | ✅  |
| [QuantifierRemoval](#quantifierremoval)                         | ✅  |
| [QuantifierNChange](#quantifiernchange)                         |     | ✅  | ✅  |
| [QuantifierNOrMoreModification](#quantifiernormoremodification) |     | ✅  | ✅  |
| [QuantifierNOrMoreChange](#quantifiernormorechange)             |     | ✅  | ✅  |
| [QuantifierNMModification](#quantifiernmmodification)           |     | ✅  | ✅  |
| [QuantifierShortModification](#quantifiershortmodification)     |     | ✅  | ✅  |
| [QuantifierShortChange](#quantifiershortchange)                 |     | ✅  | ✅  |
| [QuantifierReluctantAddition](#quantifierreluctantaddition)     |     |     | ✅  |
| [GroupToNCGroup](#grouptoncgroup)                               |     | ✅  | ✅  |

## Boundary Mutators

### BOLRemoval

Remove the beginning of line character `^`.

| Original | Mutated |
| -------- | ------- |
| `^abc`   | `abc`   |

[Back to table 🔝](#supported-mutators)

### EOLRemoval

Remove the end of line character `$`.

| Original | Mutated |
| -------- | ------- |
| `abc$`   | `abc`   |

[Back to table 🔝](#supported-mutators)

### BOL2BOI

Change the beginning of line character `^` to a beginning of input character `\A`.

| Original | Mutated |
| -------- | ------- |
| `^abc`   | `\Aabc` |

[Back to table 🔝](#supported-mutators)

### EOL2EOI

Change the end of line character `^` to a end of input character `\z`.

| Original | Mutated |
| -------- | ------- |
| `abc$`   | `abc\z` |

[Back to table 🔝](#supported-mutators)

## Character class mutators

### CharClassNegation

Flips the sign of a character class.

| Original | Mutated  |
| -------- | -------- |
| `[abc]`  | `[^abc]` |
| `[^abc]` | `[abc]`  |

[Back to table 🔝](#supported-mutators)

### CharClassChildRemoval

Remove a child of a character class.

| Original | Mutated |
| -------- | ------- |
| `[abc]`  | `[bc]`  |
| `[abc]`  | `[ac]`  |
| `[abc]`  | `[ab]`  |

[Back to table 🔝](#supported-mutators)

### CharClassAnyChar

Change a character class to a character class which matches any character.

| Original | Mutated  |
| -------- | -------- |
| `[abc]`  | `[\w\W]` |

[Back to table 🔝](#supported-mutators)

### CharClassRangeModification

Change the high and low of a range by one in both directions if possible.

| Original | Mutated |
| -------- | ------- |
| `[b-y]`  | `[a-y]` |
| `[b-y]`  | `[c-y]` |
| `[b-y]`  | `[b-z]` |
| `[b-y]`  | `[b-x]` |

[Back to table 🔝](#supported-mutators)

## Predefined character class mutators

### PredefCharClassNegation

Flips the sign of a predefined character class. All the predefined character classes are shown in the table below.

| Original | Mutated |
| -------- | ------- |
| `\d`     | `\D`    |
| `\D`     | `\d`    |
| `\s`     | `\S`    |
| `\S`     | `\s`    |
| `\w`     | `\W`    |
| `\W`     | `\w`    |

[Back to table 🔝](#supported-mutators)

### PredefCharClassNullification

Remove the backslash from a predefined character class such as `\w`.

| Original | Mutated |
| -------- | ------- |
| `\d`     | `d`     |
| `\D`     | `D`     |
| `\s`     | `s`     |
| `\S`     | `S`     |
| `\w`     | `w`     |
| `\W`     | `W`     |

[Back to table 🔝](#supported-mutators)

### PredefCharClassAnyChar

Change a predefined character class to a character class containing the predefined one and its
negation.

| Original | Mutated  |
| -------- | -------- |
| `\d`     | `[\d\D]` |
| `\D`     | `[\D\d]` |
| `\s`     | `[\s\S]` |
| `\S`     | `[\S\s]` |
| `\w`     | `[\w\W]` |
| `\W`     | `[\W\w]` |

[Back to table 🔝](#supported-mutators)

## Quantifier mutators

### QuantifierRemoval

Remove a quantifier. This is done for all possible quantifiers, even ranges, and the reluctant
and possessive variants.

| Original    | Mutated |
| ----------- | ------- |
| `abc?`      | `abc`   |
| `abc*`      | `abc`   |
| `abc+`      | `abc`   |
| `abc{1,3}`  | `abc`   |
| `abc??`     | `abc`   |
| `abc*?`     | `abc`   |
| `abc+?`     | `abc`   |
| `abc{1,3}?` | `abc`   |
| `abc?+`     | `abc`   |
| `abc*+`     | `abc`   |
| `abc++`     | `abc`   |
| `abc{1,3}+` | `abc`   |

[Back to table 🔝](#supported-mutators)

### QuantifierNChange

Change the fixed amount quantifier to a couple of range variants.

| Original | Mutated    |
| -------- | ---------- |
| `abc{9}` | `abc{0,9}` |
| `abc{9}` | `abc{9,}`  |

[Back to table 🔝](#supported-mutators)

### QuantifierNOrMoreModification

Change the `n` to infinity range quantifier to a couple of variants where the low of the range is
incremented and decremented by one.

| Original  | Mutated    |
| --------- | ---------- |
| `abc{9,}` | `abc{8,}`  |
| `abc{9,}` | `abc{10,}` |

[Back to table 🔝](#supported-mutators)

### QuantifierNOrMoreChange

Turn an `n` or more range quantifier into a fixed number quantifier.

| Original  | Mutated  |
| --------- | -------- |
| `abc{9,}` | `abc{9}` |

[Back to table 🔝](#supported-mutators)

### QuantifierNMModification

Alter the `n` to `m` range quantifier by decrementing or incrementing the high and low of the
range by one.

| Original   | Mutated     |
| ---------- | ----------- |
| `abc{3,9}` | `abc{2,9}`  |
| `abc{3,9}` | `abc{4,9}`  |
| `abc{3,9}` | `abc{3,8}`  |
| `abc{3,9}` | `abc{3,10}` |

[Back to table 🔝](#supported-mutators)

### QuantifierShortModification

Treat the shorthand quantifiers (`?`, `*`, `+`) as their corresponding range quantifier
variant (`{0,1}`, `{0,}`, `{1,}`), and applies the same mutations as mentioned in the mutators
above.

| Original | Mutated    |
| -------- | ---------- |
| `abc?`   | `abc{1,1}` |
| `abc?`   | `abc{0,0}` |
| `abc?`   | `abc{0,2}` |
| `abc*`   | `abc{1,}`  |
| `abc+`   | `abc{0,}`  |
| `abc+`   | `abc{2,}`  |

[Back to table 🔝](#supported-mutators)

### QuantifierShortChange

Change the shorthand quantifiers `*` and `+` to their fixed range quantifier variant.

| Original | Mutated  |
| -------- | -------- |
| `abc*`   | `abc{0}` |
| `abc+`   | `abc{1}` |

[Back to table 🔝](#supported-mutators)

### QuantifierReluctantAddition

Change greedy quantifiers to reluctant quantifiers.

| Original    | Mutated      |
| ----------- | ------------ |
| `abc?`      | `abc??`      |
| `abc*`      | `abc*?`      |
| `abc+`      | `abc+?`      |
| `abc{9}`    | `abc{9}?`    |
| `abc{9,}`   | `abc{9,}?`   |
| `abc{9,13}` | `abc{9,13}?` |

[Back to table 🔝](#supported-mutators)

## Group mutators

### GroupToNCGroup

Change a normal group to a non-capturing group.

| Original | Mutated   |
| -------- | --------- |
| `(abc)`  | `(?:abc)` |

[Back to table 🔝](#supported-mutators)

_Copyright 2021 Stryker mutator team_