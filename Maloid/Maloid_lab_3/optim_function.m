%% Оптимізуюча функція
function [ result ] = optim_function( way, array)

sum = 0;
for i = 1:1:length(way)-1
    sum = sum + array(way(i), way(i+1));
end

result = sum;
end