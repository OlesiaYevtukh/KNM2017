function ii=roulette_wheel_indexes(m,prn)
cpr=cumsum(prn);
n=length(prn);
sz=size(cpr);
if sz(1)>sz(2)
    cpr1=cpr';
else
    cpr1=cpr;
end
i1=interp1([0 cpr1],1:(n+1),rand(1,m),'linear');
ii=floor(i1);