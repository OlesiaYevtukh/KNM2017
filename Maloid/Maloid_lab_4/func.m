%% Функція
function [ result ] = func( x )

a=4;
b=-5;
c=-26;
d=2;

result = a+b*x+c*x.^2+d*x.^3;

end