%% Варіант 5

%% Стартова популяція
startPopulation = [
    6, 10, 1, 2, 7, 3, 4, 9, 5, 8;
    2, 5, 3, 9, 6, 7, 10, 4, 8, 1;
    6, 7, 10, 8, 9, 3, 4, 5, 1, 2;
    7, 8, 4, 9, 6, 2, 10, 1, 5, 3;
    7, 10, 5, 8, 9, 2, 6, 1, 3, 4
    ];

%% Масив відстаней між містами
n = 10;                             % кількість міст
array = randi(1000, n, n);           % діапазон відстаней 1-1000
for i=1:1:n
    array(i,i)=100000;
end
disp('Array of distance')
disp(array)

%% Параметри ГА
options = gaoptimset(...
    'EliteCount', 0, ...
    'PopulationSize', 5, ...
    'InitialPopulation', startPopulation, ...
    'MutationFcn', @MutationFcn, ...
    'CrossoverFcn', @CrossoverFcn, ...
    'TimeLimit', 3 ...
    );

%% Запуск методу
[x,fval,exitflag,output,population,scores] = ga(@(x)optim_function(x, array), 10, options);

%% Відображення результатів
disp('Best child:');
disp(x);
fprintf('f(x) = %d\n', fval);

disp('Last population:');
for i=1:1:5
    fprintf('\t%d', population(i,:));
    fprintf('\t=>\t%d\n', scores(i));
end;
