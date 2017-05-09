%% Мутація інвертування із зсувом


function [  mutationChildren ] = MutationFcn( parents, options, nvars, ...
    FitnessFcn, state, thisScore, thisPopulation )

% parents - номер особини в популяції, що мутує
% nvars - кількість змінних
% thisPopulation - поточна популяція

mutant = thisPopulation(parents, :);               %особина, що мутує
offset = randi(nvars);                             %випадковий крок зміщення
mutationChildren = circshift(mutant',offset)';       % зміщення вправо на крок

end

%% Кінець
