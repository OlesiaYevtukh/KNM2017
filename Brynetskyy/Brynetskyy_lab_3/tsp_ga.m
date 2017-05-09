kst=40; % кількість міст
plot_size=10; % розмір області asx x asz

pop_kst=300; % чисельність популяції
pokol_kst=1500; % Кількість поколінь

pm=0.01; % імовірність мутації двох міст
pm2=0.02; % імовірність мутації двох частин шляху
pmf=0.08; % імовірність мутації випадкової частини шляху

graph=plot_size*rand(2,kst); % випадкове розподілення міст
vid_mat=zeros(kst,kst); % матриця відстаней

for n1=1:kst-1
    r1=graph(:,n1);
    for n2=n1+1:kst
        r2=graph(:,n2);
        dr=r1-r2;
        dr2=dr'*dr;
        drl=sqrt(dr2);
        vid_mat(n1,n2)=drl;
        vid_mat(n2,n1)=drl;
    end
end

% Почати з випадкових замкнених шляхів:
G=zeros(pop_kst,kst); 
for psc=1:pop_kst
    G(psc,:)=randperm(kst);
end

plot(1)

% вивід найкращого шляху:
hpb=plot(NaN,NaN,'r-');
ht=title(' ');
hold on;
% вивід ділянок вузла
for n=1:kst
    text(graph(1,n),graph(2,n),num2str(n));
end

plot(graph(1,:),graph(2,:),'k.'); % виводити міста як чорні точки

axis equal;

%встановлення розміру графіка
xlim([-0.1*plot_size 1.1*plot_size]);
ylim([-0.1*plot_size 1.1*plot_size]);

pthd=zeros(pop_kst,1); %довжина шляху
p=zeros(pop_kst,1); % імовірності

%цикл поколінь
for gc=1:pokol_kst
    % знайти довжину шляху:
    for psc=1:pop_kst
        Gt=G(psc,:);
        pt=0; % підсумовування довжини шляху
        for nc=1:kst-1
            pt=pt+vid_mat(Gt(nc),Gt(nc+1));
        end
        % останній і перший:
        pt=pt+vid_mat(Gt(kst),Gt(1));
        pthd(psc)=pt;
    end
    ipthd=1./pthd; % зворотня довжина шляху
    p=ipthd/sum(ipthd); % імовірності 
    
    [mbp bp]=max(p); 
    Gb=G(bp,:); % найкращий шлях    
    
    % Оновлення фігури
    if mod(gc,5)==0
        set(hpb,'Xdata',[graph(1,Gb) graph(1,Gb(1))],'YData',[graph(2,Gb) graph(2,Gb(1))]);
        set(ht,'string',['Покоління: ' num2str(gc)  '  Найкраща довжина шляху: ' num2str(pthd(bp))]);
        drawnow;
    end
    
   % схрещування:
    ii=roulette_wheel_indexes(pop_kst,p); % іі - гени номери генів міст, що будуть використані
    Gc=G(ii,:); % гени для схрещування
    Gch=zeros(pop_kst,kst); % діти
    for prc=1:(pop_kst/2) % підрахунок пар
        i1=1+2*(prc-1);
        i2=2+2*(prc-1);
        g1=Gc(i1,:); % перший ген
        g2=Gc(i2,:); % другий ген
        cp=ceil((kst-1)*rand); % точка схрещування, випадкове число в інтервалі [1; nn-1]
        % двоє дітей:
        g1ch=insert_begining(g1,g2,cp);
        g2ch=insert_begining(g2,g1,cp);
        Gch(i1,:)=g1ch;
        Gch(i2,:)=g2ch;
    end
    G=Gch; % діти
    
    % мутація обміну двох випадкових міст:
    for psc=1:pop_kst
        if rand<pm
            rnp=ceil(kst*rand); % випадкове число міст для мутації
            rpnn=randperm(kst);
            ctp=rpnn(1:rnp); %вибрати rnp випадкових міст для мутації
            Gt=G(psc,ctp); % отримати ці міста зі списку
            Gt=Gt(randperm(rnp)); % переставити міста
            G(psc,ctp)=Gt; % % повернути міста назад
         end
    end   
    
    % мутація обміну 2 частин шляху:
    for psc=1:pop_kst
        if rand<pm2
            cp=1+ceil((kst-3)*rand); % діапазон [2 nn-2]
            G(psc,:)=[G(psc,cp+1:kst) G(psc,1:cp)];
        end
    end   
    
    % мутація обміну випадкової частини шляху:
    for psc=1:pop_kst
        if rand<pmf
            n1=ceil(kst*rand);
            n2=ceil(kst*rand);
            G(psc,n1:n2)=fliplr(G(psc,n1:n2));
        end
    end
    
   G(1,:)=Gb; % елітарність  
end
