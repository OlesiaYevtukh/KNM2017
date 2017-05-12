function laba4

firstPoint=-10;
lastPoint=53;

x=firstPoint:0.01:lastPoint;

figure
plot(x,fitness_decimal(x))

title('y=2-5x+47x^2-3x^3')
xlabel('x')
ylabel('y')

grid on
hold on

x_min=fminbnd(@fitness_decimal,10,20);               
x_max=fminbnd(@(x)-fitness_decimal(x),firstPoint,20);       

plot(x_min,fitness_decimal(x_min),'ro') 
plot(x_max,fitness_decimal(x_max),'bo')

legend('y(x)', 'min', 'max')


startPopulation = randi([0 1], 6, 6); 


options = gaoptimset(...
    'EliteCount', 1, ...
    'PopulationSize', 6, ...
    'InitialPopulation', startPopulation, ...  
    'MutationFcn', @MutationFcn, ...
    'CrossoverFcn', @crossoversinglepoint, ...
    'TimeLimit', 30 ...
    );

[x1,fval1,exitflag1,output1,population_min,scores1] = ga(@fitness_binary, 6, options);
[x2,fval2,exitflag2,output2,population_max,scores2] = ga(@fitness_binary_max, 6, options);



plot(bi2de(x1)-10,fval1,'g*') 
plot(bi2de(x2)-10,-fval2,'r*') 

legend('y(x)', 'min', 'max', 'min(ga)', 'max(ga)')

disp('Min:');
disp(x1);
fprintf('Decimal = %d\n', bi2de(x1)-10);
fprintf('f(x) = %d\n', fval1);

disp('Last population(MIN):');
for i=1:1:6
    fprintf('\t%d', population_min(i,:));
    fprintf('\t(%d)', bi2de(population_min(i,:))-10);
    fprintf('\t=>\t%d\n', scores1(i));
end;

disp('Max:');
disp(x2);
fprintf('Decimal = %d\n', bi2de(x2)-10);
fprintf('f(x) = %d\n', -fval2);

disp('Last population(MAX):');
for i=1:1:6
    fprintf('\t%d', population_max(i,:));
    fprintf('\t(%d)', bi2de(population_max(i,:))-10);
    fprintf('\t=>\t%d\n', -scores2(i));
end;
end