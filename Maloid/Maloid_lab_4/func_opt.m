%% Оптимізуюча функція
function [ result ] = func_opt( binary )

a=4;
b=-5;
c=-26;
d=2;

x=bi2de(binary)-10;



result = a+b*x+c*x^2+d*x^3;


if(x<-2)
    result=0;
end


end