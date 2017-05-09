%% Варіант 5
clear, clc, close all
dbstop if error

%% Масив відстаней між містами

n = 20;                             % кількість міст
coord = randi(100, 2, n);           % випадкові координати міст

distance = zeros(n, n);           
for i=1:1:n
    for j=1:1:n
        distance(i,j) = int32(pdist([coord(1,i),coord(2,i);coord(1,j),coord(2,j),],'euclidean'));        
    end
end

disp('Coordinates')
disp(coord)

disp('Array of distance')
disp(distance)

%% Стартова популяція
populationSize = 30;
startPopulation = zeros(populationSize,n);
aa = randsample(n,n);
for i=1:1:populationSize   
    startPopulation (i,:) = randsample(n,n);
end

disp('StartPopulation')
disp(startPopulation)
%% Параметри ГА
options = gaoptimset(...
    'EliteCount', populationSize/2, ...
    'PopulationSize', populationSize, ...
    'InitialPopulation', startPopulation, ...
    'MutationFcn', @MutationFcn, ...
    'CrossoverFcn', @CrossoverFcn, ...
    'SelectionFcn', {@selectiontournament,2}, ...
    'PlotFcns', {@gaplotbestf} ...
);

%% Запуск методу
[xx,fval,exitflag,output,population,scores] = ga(@(x)optim_function(x, distance), n, options);

%% Відображення результатів
disp('Best child:');
disp(xx);
fprintf('f(x) = %d\n', fval);

figure
plot(coord(1,:),coord(2,:),'bo');

grid on
hold on
for i=1:1:n
    if (i==n)
        plot([coord(1,xx(i)),coord(1,xx(1))],[coord(2,xx(i)),coord(2,xx(1))],'r--')
        text((coord(1,xx(i))+coord(1,xx(1)))/2,(coord(2,xx(i))+coord(2,xx(1)))/2, num2str(distance(xx(i),xx(1))))            
    else
        plot([coord(1,xx(i)),coord(1,xx(i+1))],[coord(2,xx(i)),coord(2,xx(i+1))])
        text((coord(1,xx(i))+coord(1,xx(i+1)))/2,(coord(2,xx(i))+coord(2,xx(i+1)))/2, num2str(distance(xx(i),xx(i+1))))
    end
    text(coord(1,xx(i))+2,coord(2,xx(i))+2,num2str(xx(i)))
end


disp('Last population:');
for i=1:1:n
    fprintf('\t%d', population(i,:));
    fprintf('\t=>\t%d\n', scores(i));
end;

