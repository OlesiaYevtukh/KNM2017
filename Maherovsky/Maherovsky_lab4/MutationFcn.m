function [  mutationChildren ] = MutationFcn( parents, options, nvars, ...
FitnessFcn, state, thisScore, thisPopulation )

mutant = thisPopulation(parents, :);
 
positions=[randi(nvars),randi(nvars)];
positions=sort(positions);
diff=positions(2)-positions(1);
avg=(positions(2)+positions(1))/2;

if(diff>1)
    middle = round(avg);
    for i=positions(1):1:positions(2)
      if (i+1)==middle
          break;
      end;
        temp=mutant(i+1);
        mutant(i+1)=mutant(positions(2)-1);
        mutant(positions(2)-1)=temp;
    end;
end;
 
mutationChildren = mutant;
 
end