%define __jar_repack 0
Name: dans.knaw.nl-kafka-experiment
Version: 1.0
Release: SNAPSHOT20180109090126
Summary: kafka-experiment
License: Apache 2.0
Vendor: dans.knaw.nl
Group: Applications/Archiving
Packager: dans.knaw.nl
Requires: java-1.8.0-openjdk
autoprov: yes
autoreq: yes
BuildArch: noarch
BuildRoot: /Users/RichardvanHeest/git/elastic-experiments/kafka/target/rpm/dans.knaw.nl-kafka-experiment/buildroot

%description

%install

if [ -d $RPM_BUILD_ROOT ];
then
  mv /Users/RichardvanHeest/git/elastic-experiments/kafka/target/rpm/dans.knaw.nl-kafka-experiment/tmp-buildroot/* $RPM_BUILD_ROOT
else
  mv /Users/RichardvanHeest/git/elastic-experiments/kafka/target/rpm/dans.knaw.nl-kafka-experiment/tmp-buildroot $RPM_BUILD_ROOT
fi

ln -s /opt/dans.knaw.nl/kafka-experiment/bin/kafka-experiment $RPM_BUILD_ROOT/opt/bin/kafka-experiment
ln -s /opt/dans.knaw.nl/kafka-experiment/bin/kafka-experiment-1.0-SNAPSHOT.jar $RPM_BUILD_ROOT/opt/dans.knaw.nl/kafka-experiment/bin/kafka-experiment.jar


%files
%defattr(744,kafka-experiment,kafka-experiment,755)
 "/opt/dans.knaw.nl/kafka-experiment/lib"
 "/opt/dans.knaw.nl/kafka-experiment/bin"
%attr(755,kafka-experiment,kafka-experiment)  "/opt/dans.knaw.nl/kafka-experiment/bin/kafka-experiment"
%config  "/etc/opt/dans.knaw.nl/kafka-experiment"
  "/opt/bin/kafka-experiment"
  "/opt/dans.knaw.nl/kafka-experiment/bin//kafka-experiment.jar"

%prep

%pretrans

%pre

%post

%preun

%postun

%posttrans

%verifyscript

%clean
