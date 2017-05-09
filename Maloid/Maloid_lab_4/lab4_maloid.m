clear, clc, close all
dbstop if error

%% Графік заданої функції 

x_start=-10;
x_end=20;

x=x_start:0.01:x_end;

figure
plot(x,func(x))

title('y=4-5x-26x^2+2x^3')
xlabel('x')
ylabel('y')

grid on
hold on

x_min=fminbnd(@func,x_start,x_end);                % х - мінімуму
x_max=fminbnd(@(x)-func(x),x_start,20);         % х - максимуму

plot(x_min,func(x_min),'ro') 
plot(x_max,-func(x_max),'bo')

legend('y(x)', 'min', 'max')

%% Стартова популяція

startPopulation = randi([0 1], 10, 6);  % масив бітів 10х6

%% Параметри ГА

options = gaoptimset(...
    'EliteCount', 1, ...
    'PopulationSize', 10, ...
    'InitialPopulation', startPopulation, ...  
    'MutationFcn', @MutationFcn, ...
    'CrossoverFcn', @crossoversinglepoint, ...
    'TimeLimit', 30 ...
    );


%% Запуск методу
[x1,fval1,exitflag1,output1,population1,scores1] = ga(@func_opt, 6, options);
[x2,fval2,exitflag2,output2,population2,scores2] = ga(@func_opt_max, 6, options);


%% Відображення результатів

plot(bi2de(x1)-10,fval1,'g*') 
plot(bi2de(x2)-10,-fval2,'r*') 

legend('y(x)', 'min', 'max', 'min(ga)', 'max(ga)')

disp('Min:');
disp(x1);
fprintf('Decimal = %d\n', bi2de(x1)-10);
fprintf('f(x) = %d\n', fval1);

disp('Last population:');
for i=1:1:10
    fprintf('\t%d', population1(i,:));
    fprintf('\t(%d)', bi2de(population1(i,:))-10);
    fprintf('\t=>\t%d\n', scores1(i));
end;

disp('Max:');
disp(x2);
fprintf('Decimal = %d\n', bi2de(x2)-10);
fprintf('f(x) = %d\n', -fval2);

disp('Last population:');
for i=1:1:10
    fprintf('\t%d', population2(i,:));
    fprintf('\t(%d)', bi2de(population2(i,:))-10);
    fprintf('\t=>\t%d\n', -scores2(i));
end;
