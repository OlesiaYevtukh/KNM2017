function gch=insert_begining(g1,g2,cp)
for cpc=1:cp
    if g2(cpc)~=g1(cpc) 
        ind=find(g1==g2(cpc)); 
        g1(ind)=g1(cpc);
        g1(cpc)=g2(cpc);
    end
end
gch=g1;