## String format for CNF

The strings given to the `CNFFactory` must match the following pattern:

- Each formula must contain at least 1 clause.
- Each clause must start with `"("` and end with `")"`.
- Each clause must have at least 1 and at most 3 literals.
- Each clause must be separated with `"^"`.
- If you want to negate a variable for a literal, use `"~"`. Note: It doesn't matter, whether you have a white space between the `"~"` and the name of the variable or not.
- Each literal must be separated by a `"v"`.
- The name of a variable must not consist of the previously mentioned characters.

### Examples for valid strings
```kotlin
"(A v B v C) ^ (~A v B v ~D)"
"(~ A)"
"(~ThisIsOneValidName v 42)"
```

### Examples for invalid strings
```kotlin
"(A ^ B)"
"(A v ~B) ^ ~C ^ (C v D)"
"A v B"
"(A v B) v (C v D)"
"~(A v B) ^ ~(C v D)"
"(ThisIsAnInvalidName)"
```

## External Sources

### Glucose 4.1

The executable is contained inside the `glucose` directory. You can find more infos about `glucose` at [https://github.com/jakublevy/glucose-win/releases/tag/v4.1](https://github.com/jakublevy/glucose-win/releases/tag/v4.1)
