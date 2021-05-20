pkg load signal

% sampling frequency
ts = 0.1;  % [s]
fs = 1/ts; % [Hz]

%% Test signal n
% frequency components
fcomp = [0.1, 0.2, 0.7, 1.0]; % [Hz]

% signal (in sample domain)
signal = @(n)( sin(2*pi*fcomp(1)*(n/fs)) + ...
               sin(2*pi*fcomp(2)*(n/fs)) + ...
               sin(2*pi*fcomp(3)*(n/fs)) + ...
               sin(2*pi*fcomp(4)*(n/fs)) );
               
nvec = 0 : 10^4 - 1;
xvec = signal(nvec)';   
% test signal amplitude spectrum
% frequency vector
frange = (-1/2 : 1/length(nvec) : 1/2-1/length(nvec)); % [-]
fxvec = frange*fs;                               % [Hz]
% amplitude response
Axvec = abs(fftshift(fft(xvec,length(nvec)))); % [-]
Axvec = Axvec / length(nvec);
        
%% Filter desired parameters
% cut-off frequency
f1 = 0.5; % [Hz]
% frequency at the end transition band
f2 = 0.8; % [Hz]
% transition band length
df = f2 - f1;
% stopband attenuation of A [dB] at f2 [Hz]
A = 60; % [dB]
               
%% Filter order estimation
% with 'fred harris rule of thumb'
N = round( (fs/df)*A / 22 );

%% Filter object with fir1 funcion
% normalised frequency
w = f1 / (fs/2); % [-]
% low pass filter
H = fir1(N, w, 'low');

%% Filter frequency response
% no. of samples
n = 10^4; % [-]
% frequency vector
frange = (-1/2 : 1/n : 1/2-1/n); % [-]
fhvec = frange*fs;               % [Hz]
% amplitude response
Ahvec_v1 = abs(freqz(H, 1, 2*pi*frange)); % [-] frequency response
Ahvec_v1 = 20*log10(Ahvec_v1); % [dB]
Ahvec_v2 = abs(fftshift(fft(H,n)));       % [-] impulse response fft 
Ahvec_v2 = 20*log10(Ahvec_v2); % [dB]

%% Test signal filtration
xfvec = filter(H,1,xvec);

% filtered signal amplitude spectrum
% amplitude response
Axfvec = abs(fftshift(fft(xfvec,length(nvec)))); % [-]
Axfvec = Axfvec / length(nvec);

%% Test signal filtratrion - reference implementation
b = H;        % feedforward filter coefficients
x_state = zeros(size(H));   % initial state
xfvec2 = zeros(size(xvec)); % filtration result

for i = 1 : length(xvec);
  [xfvec2(i), x_state] = myFIR(xvec(i), x_state, b);
endfor

%% Plot results
fminmax = [0 1.5];
tminmax = [0 50];
Aminmax = [-3 3];
AdBminmax = [-120 5];

subplot(2,2,1)
  plot(nvec/fs, xvec, 'b'); grid on;
  xlabel("Time [s]");
  ylabel("Amplitude [-]");
  axis([tminmax Aminmax]);
  title('Orginal test signal time series');
  hold off;
subplot(2,2,2)
  ax = plotyy (fxvec, Axvec, ...
               fhvec, Ahvec_v1, ...
               @plot, @plot); hold on; grid on;
  xlabel("Frequency [Hz]");
  ylabel(ax(1), "Amplitude spectrum [-]");
  ylabel(ax(2), "Amplitude response [dB]"); 
  plot([f1 f1], AdBminmax, 'k--');
  plot([f2 f2], AdBminmax, 'k--'); 
  set(ax(1),'YLim', [0 0.51], 'XLim', fminmax);
  set(ax(2),'YLim', AdBminmax, 'XLim', fminmax);
  set(ax(2),'XTick',[], 'xcolor',[1 1 1]);
  title('Original test signal ampltitude spectrum');
  hold off;
subplot(2,2,3)
  plot(nvec/fs, xfvec, 'b'); grid on; hold on;
  plot(nvec/fs, xfvec2, 'g--');
  xlabel("Time [s]");
  ylabel("Amplitude [-]");
  axis([tminmax Aminmax]);
  legend('filter function', 'myFIR function');
  title('Filtered test signal time series');
  hold off;
subplot(2,2,4)
  ax = plotyy (fxvec, Axfvec, ...
               fhvec, Ahvec_v2, ...
               @plot, @plot); hold on; grid on;
  xlabel("Frequency [Hz]");
  ylabel(ax(1), "Amplitude spectrum [-]");
  ylabel(ax(2), "Amplitude response [dB]"); 
  plot([f1 f1], AdBminmax, 'k--');
  plot([f2 f2], AdBminmax, 'k--'); 
  set(ax(1),'YLim', [0 0.51], 'XLim', fminmax);
  set(ax(2),'YLim', AdBminmax, 'XLim', fminmax);
  set(ax(2),'XTick',[], 'xcolor',[1 1 1]);
  title('Filtered test signal ampltitude spectrum');
  hold off;

%% Export result
c = input("Export filter? (Y/N): ",'s');
if (c=='Y') || (c=='y')

% FIR Low pass filter parameters
path = { 'C:\localhost\FIR Example\',
         'C:\PP\Dydaktyka\AMiWdIP-L\Desktop\FIR Example\filter_example\Model\',
         'C:\PP\Dydaktyka\AMiWdIP-L\Android\FIR Example\app\src\main\java\iot\examples\firexample\' };
filename = 'MyFirData';
ex = {'.js', '.cs', '.java' };

% to web client (JavaScript)
exportFIRtoJS(b, zeros(size(b)), ts, 18, [path{1} filename ex{1}]);

% to desktop client (C#)
exportFIRtoCSharp(b, zeros(size(b)), ts, 18, 'filter_example.Model', ...
                    [path{2} filename ex{2}]);

% to mobile client (JAVA)
exportFIRtoJava(b, zeros(size(b)), ts, 18, 'iot.examples.firexample', ...
                   [path{3} filename ex{3}]);

% Unit Test data
path = { 'C:\localhost\FIR Example\UnitTest\',
         'C:\PP\Dydaktyka\AMiWdIP-L\Desktop\FIR Example\filter_exampleTests\Model\',
         'C:\PP\Dydaktyka\AMiWdIP-L\Android\FIR Example\app\src\test\java\iot\examples\firexample\' };
filename = 'MyFirTestData';
ex = {'.js', '.cs', '.java' };
  
% to web client (JavaScript)
exportTestDatatoJS(xvec(1:500), xfvec2(1:500), 18, [path{1} filename ex{1}]);

% to desktop client (C#)
exportTestDatatoCSharp(xvec(1:500), xfvec2(1:500), 18, 'filter_example.Model.Tests',...
                       [path{2} filename ex{2}]);

% to mobile client (JAVA)
exportTestDatatoJava(xvec(1:500), xfvec2(1:500), 18, 'iot.examples.firexample',...
                       [path{3} filename ex{3}]);
  
endif
