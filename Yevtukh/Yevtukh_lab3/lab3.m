
function varargout = lab3(varargin)
      
    defaultConfig.xy          = 10*rand(20,2);
    defaultConfig.dmat        = [];
    defaultConfig.popSize     = 40;
    defaultConfig.numIter     = 1e4;
    defaultConfig.showProg    = true;
    defaultConfig.showResult  = true;
    defaultConfig.showWaitbar = false;
    
    if ~nargin
        userConfig = struct();
    elseif isstruct(varargin{1})
        userConfig = varargin{1};
    else
        try
            userConfig = struct(varargin{:});
        catch
            error('Expected inputs are either a structure or parameter/value pairs');
        end
    end
    configStruct = get_config(defaultConfig,userConfig);
    xy          = configStruct.xy;
    dmat        = configStruct.dmat;
    popSize     = configStruct.popSize;
    numIter     = configStruct.numIter;
    showProg    = configStruct.showProg;
    showResult  = configStruct.showResult;
    showWaitbar = configStruct.showWaitbar;
    if isempty(dmat)
        nPoints = size(xy,1);
        a = meshgrid(1:nPoints);
        dmat = reshape(sqrt(sum((xy(a,:)-xy(a',:)).^2,2)),nPoints,nPoints);
    end
    

    [N,dims] = size(xy);
    [nr,nc] = size(dmat);
    if N ~= nr || N ~= nc
        error('Invalid XY or DMAT inputs!')
    end
    n = N;
    

    popSize     = 4*ceil(popSize/4);
    numIter     = max(1,round(real(numIter(1))));
    showProg    = logical(showProg(1));
    showResult  = logical(showResult(1));
    showWaitbar = logical(showWaitbar(1));
 
    pop = zeros(popSize,n);
    pop(1,:) = (1:n);
    for k = 2:popSize
        pop(k,:) = randperm(n);
    end
    
  
    globalMin = Inf;
    totalDist = zeros(1,popSize);
    distHistory = zeros(1,numIter);
    tmpPop = zeros(4,n);
    newPop = zeros(popSize,n);
    if showProg
        figure('Name','Lab_3','Numbertitle','off');
        hAx = gca;
    end
    if showWaitbar
        hWait = waitbar(0,'Searching for near-optimal solution ...');
    end
    for iter = 1:numIter
       
        for p = 1:popSize
            d = dmat(pop(p,n),pop(p,1)); 
            for k = 2:n
                d = d + dmat(pop(p,k-1),pop(p,k));
            end
            totalDist(p) = d;
        end
        
       
        [minDist,index] = min(totalDist);
        distHistory(iter) = minDist;
        if minDist < globalMin
            globalMin = minDist;
            optRoute = pop(index,:);
            if showProg
               
                rte = optRoute([1:n 1]);
                if dims > 2, plot3(hAx,xy(rte,1),xy(rte,2),xy(rte,3),'r.-');
                else plot(hAx,xy(rte,1),xy(rte,2),'r.-'); end
                title(hAx,sprintf('Total Distance = %1.4f, Iteration = %d',minDist,iter));
                drawnow;
            end
        end
        
       
        randomOrder = randperm(popSize);
        for p = 4:4:popSize
            rtes = pop(randomOrder(p-3:p),:);
            dists = totalDist(randomOrder(p-3:p));
            [ignore,idx] = min(dists); 
            bestOf4Route = rtes(idx,:);
            routeInsertionPoints = sort(ceil(n*rand(1,2)));
            I = routeInsertionPoints(1);
            J = routeInsertionPoints(2);
            for k = 1:4 
                tmpPop(k,:) = bestOf4Route;
                switch k
                    case 2
                        tmpPop(k,I:J) = tmpPop(k,J:-1:I);
                    case 3 
                        tmpPop(k,[I J]) = tmpPop(k,[J I]);
                    case 4 
                        tmpPop(k,I:J) = tmpPop(k,[I+1:J I]);
                    otherwise 
                end
            end
            newPop(p-3:p,:) = tmpPop;
        end
        pop = newPop;
        
      
        if showWaitbar && ~mod(iter,ceil(numIter/325))
            waitbar(iter/numIter,hWait);
        end
        
    end
    if showWaitbar
        close(hWait);
    end
    
    
    

    if nargout
        resultStruct = struct( ...
            'xy',          xy, ...
            'dmat',        dmat, ...
            'popSize',     popSize, ...
            'numIter',     numIter, ...
            'showProg',    showProg, ...
            'showResult',  showResult, ...
            'showWaitbar', showWaitbar, ...
            'optRoute',    optRoute, ...
            'minDist',     minDist);
        
        varargout = {resultStruct};
    end
    
end


function config = get_config(defaultConfig,userConfig)
    
 
    config = defaultConfig;
    

    defaultFields = fieldnames(defaultConfig);
    
   
    userFields = fieldnames(userConfig);
    nUserFields = length(userFields);
    
    for i = 1:nUserFields
        userField = userFields{i};
        isField = strcmpi(defaultFields,userField);
        if nnz(isField) == 1
            thisField = defaultFields{isField};
            config.(thisField) = userConfig.(userField);
        end
    end
    
end

