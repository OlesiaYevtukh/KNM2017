function [ result ] = fitness_decimal( x )

a=2;
b=-5;
c=47;
d=-3;

result = a+b*x+c*x.^2+d*x.^3;

end