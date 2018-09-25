# Antlr-Calculator

##Technologies: Antlr4, Maven

Java compiler used to evaluate math expressions using Antlr grammar.

## Usage

- navigate to project folder then type 'mvn exec:java'

## Example input

```
a = 5
b = 6
(a+b)*2
193
1+(4*2)^(-2)
1+4*2^2
e
log(2,5)
ln(e)
sin(pi/2)
tan(pi/3)
(1+2)*3
```

## Example output

```
22.0
193.0
1.015625
17.0
2.718281828459045
2.321928094887362
1.0
1.0
1.7320508075688767
9.0
```