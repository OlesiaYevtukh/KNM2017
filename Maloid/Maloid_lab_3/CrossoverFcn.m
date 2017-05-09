%% Двохточечне впорядковуюче схрещення (вар5)

function [xoverKids] = CrossoverFcn( parents, options, nvars, FitnessFcn, ...
    unused,thisPopulation )

% parents - індекси батьків в поточній популяції, що беруть участь у схрещуванні.
% nvars - кількість змінних (генів)
% thisPopulation - поточна популяція (матриця)

result = zeros(length(parents)/2, nvars);
for i = 1:2:length(parents)-1
    parent1 = thisPopulation(parents(i), :);
    parent2 = thisPopulation(parents(i+1), :);
    
    separator = randi([2, nvars-1],1,1);        %випадковий індекс умовного поділу
    gene1 = randi([1, separator-1],1,1);        %випадковий перший ген
    gene2 = randi([separator+1, nvars],1,1);    %випадковий другий ген
    
    child = zeros(1, nvars);                    %ініціалізація нащадка
    child(1,1:gene1) = parent1(1:gene1);        %копіювання генів зліва від 1го гена
    child(1,gene2:nvars) = parent1(gene2:nvars);%копіювання генів справа від 2го гена
    
    nextGene = gene1+1;                         %наступний ген після 1го
    inarr = ismember(parent2(1,:), child);      %перевірка наявності генів нащадка в другого батька
    for j = 1:1:nvars
        if (inarr(j) == 0)
            child(1, nextGene) = parent2(j);    %копіювання гену нащадку
            nextGene = nextGene + 1;
        end
    end
    
    result((i+1)/2,:) = child;
end;

xoverKids = result;
end


%% Кінець
