%% Îïòèì³çóş÷à ôóíêö³ÿ
function [ result ] = func_opt_max( binary )

a=4;
b=-5;
c=-26;
d=2;

x=bi2de(binary)-10;

result = -a-b*x-c*x^2-d*x^3;

if(x>10)
    result=100;
end

end