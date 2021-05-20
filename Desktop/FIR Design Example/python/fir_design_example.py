import math
import numpy as np
from scipy.fftpack import fft, fftshift
from scipy.signal import firwin, lfilter
import matplotlib.pyplot as plt
#TODO import MyFir

# sampling frequency
ts = 0.1;  # [s]
fs = 1/ts; # [Hz]

## Test signal 
# frequency components
fcomp = [0.1, 0.2, 0.7, 1.0]; # [Hz]

# signal (in sample domain)
signal = lambda n : (math.sin(2*math.pi*fcomp[0]*(n/fs))+
                     math.sin(2*math.pi*fcomp[1]*(n/fs))+
                     math.sin(2*math.pi*fcomp[2]*(n/fs))+
                     math.sin(2*math.pi*fcomp[3]*(n/fs)))

nvec = list(range(10**4))
xvec = [signal(n) for n in nvec]
# test signal amplitude spectrum
# frequency vector
frange = np.arange(-1/2, 1/2, 1/len(nvec)) # [-]
fxvec = frange*fs                          # [Hz]
# amplitude response
Axvec = abs(fftshift(fft(xvec)))     # [-]
Axvec = [a/len(nvec) for a in Axvec] # [-]

## Filter desired parameters
# cut-off frequency
f1 = 0.5 # [Hz]
# frequency at the end transition band
f2 = 0.8 # [Hz]
# transition band length
df = f2 - f1
# stopband attenuation of A [dB] at f2 [Hz]
A = 60 # [dB]
               
## Filter order estimation
# with 'fred harris rule of thumb'
N = round( (fs/df)*A / 22 )

## Filter object with fir1 funcion
# normalised frequency
w = f1 / (fs/2) # [-]
# low pass filter
H = firwin(N+1, w, pass_zero='lowpass')

##Filter frequency response
# no. of samples
n = 10**4; # [-]
# frequency vector
frange = np.arange(-1/2, 1/2, 1/n) # [-]
fhvec = frange*fs                  # [Hz]
# amplitude response
Ahvec = abs(fftshift(fft(H,n=n)))                   # [-] impulse response fft 
Ahvec = [20*math.log10(a + 1.0e-15) for a in Ahvec] # [dB]

## Test signal filtration
xfvec = lfilter(H,1,xvec)

# filtered signal amplitude spectrum
# amplitude response
Axfvec = abs(fftshift(fft(xfvec)))     # [-]
Axfvec = [a/len(nvec) for a in Axfvec] # [-]

## Test signal filtratrion - reference implementation
# TODO

## Plot results
plt.rcParams.update({'font.size': 7})
fig, axs = plt.subplots(2, 2)
plt.subplots_adjust(wspace=0.2, hspace=0.5)
fminmax = [0, 1.5]
tminmax = [0, 50]
Aminmax = [-3, 3]
AdBminmax = [-120, 5]

axs[0,0].set_title('Original test signal time series',fontweight='bold')
axs[0,0].plot([n/fs for n in nvec], xvec, linewidth=1)
axs[0,0].set_xlim(tminmax)
axs[0,0].set_ylim(Aminmax)
axs[0,0].grid(True)
axs[0,0].set_xlabel('Time [s]')
axs[0,0].set_ylabel('Amplitude [-]')

axs[0,1].set_title('Orginal test signal ampltitude spectrum',fontweight='bold')
axs[0,1].plot(fxvec, Axvec, linewidth=1)
axs[0,1].set_xlim(fminmax)
axs[0,1].set_ylim([0, 0.51])
axs[0,1].plot([f1,f1], AdBminmax, color='k', linestyle='--', linewidth=1)
axs[0,1].plot([f2,f2], AdBminmax, color='k', linestyle='--', linewidth=1)
axs[0,1].grid(True)
axs[0,1].set_ylabel('Amplitude spectrum [-]')
axs[0,1].set_xlabel('Frequency [Hz]')
ax_fr1 = axs[0,1].twinx()
ax_fr1.plot(fhvec, Ahvec, color='r',linewidth=1)
ax_fr1.set_ylim(AdBminmax)
ax_fr1.set_ylabel('Amplitude response [dB]')

axs[1,0].set_title('Filtered test signal time series',fontweight='bold')
axs[1,0].plot([n/fs for n in nvec], xfvec,linewidth=1)
axs[1,0].set_xlim(tminmax)
axs[1,0].set_ylim(Aminmax)
axs[1,0].grid(True)
axs[1,0].legend(['lfilter function'])
axs[1,0].set_xlabel('Time [s]')
axs[1,0].set_ylabel('Amplitude [-]')

axs[1,1].set_title('Filtered test signal ampltitude spectrum',fontweight='bold')
axs[1,1].plot(fxvec, Axfvec, linewidth=1)
axs[1,1].set_xlim(fminmax)
axs[1,1].set_ylim([0, 0.51])
axs[1,1].plot([f1,f1], AdBminmax, color='k', linestyle='--', linewidth=1)
axs[1,1].plot([f2,f2], AdBminmax, color='k', linestyle='--', linewidth=1)
axs[1,1].grid(True)
axs[1,1].set_ylabel('Amplitude spectrum [-]')
axs[1,1].set_xlabel('Frequency [Hz]')
ax_fr2 = axs[1,1].twinx()
ax_fr2.plot(fhvec, Ahvec, color='r',linewidth=1)
ax_fr2.set_ylim(AdBminmax)
ax_fr2.set_ylabel('Amplitude response [dB]')

plt.show()

## Export result
# TODO
