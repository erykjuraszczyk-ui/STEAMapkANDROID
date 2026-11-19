# Steam Android Hub

Fanowski panel dla Androida 13+, który ułatwia uruchomienie oficjalnego Steam Link i połączenie z komputerem z uruchomionym Steam.

> **Ważne ograniczenie techniczne:** aplikacja nie instaluje plików EXE ani nie uruchamia gier Windows natywnie na Androidzie. Gra jest instalowana i uruchamiana na komputerze. Obraz, dźwięk i sterowanie przesyła oficjalny Steam Link przez Steam Remote Play. Projekt jest niezależny i nie jest powiązany z Valve.

## Stan projektu — v0.1.0

- Natywna aplikacja Android napisana w Javie.
- Android 13+ (minSdk 33), compileSdk / targetSdk 37.
- Wykrywanie oficjalnej aplikacji Steam Link.
- Uruchamianie Steam Link, jeśli jest zainstalowany; w przeciwnym razie otwarcie strony aplikacji w Google Play.
- Wbudowana instrukcja konfiguracji Steam Remote Play.
- Brak logowania Steam, telemetrii, reklam, własnego backendu i zbędnych uprawnień.
- GitHub Actions buduje debug APK po zmianie kodu i udostępnia go jako artifact.

## Jak używać

1. Na komputerze zainstaluj i uruchom Steam, a następnie zaloguj się.
2. W Steam na komputerze otwórz **Ustawienia → Remote Play** i włącz tę funkcję.
3. Podłącz komputer i telefon do tej samej sieci lokalnej. Zalecane jest Wi-Fi 5 GHz; komputer najlepiej podłączyć do routera Ethernetem.
4. Zainstaluj oficjalną aplikację Steam Link z [Google Play](https://play.google.com/store/apps/details?id=com.valvesoftware.steamlink).
5. Otwórz Steam Android Hub i naciśnij **Uruchom / zainstaluj Steam Link**.
6. W Steam Link wybierz komputer i zakończ parowanie zgodnie z instrukcjami aplikacji.
7. Wybierz grę w Steam Link. Komputer musi być włączony, a gra działa na komputerze.

## Budowanie APK

Wymagania projektu:

- JDK 17
- Android SDK Platform 37
- Android Build Tools 36.0.0
- Android Gradle Plugin 9.5.0
- Gradle 9.6.0

W Android Studio otwórz katalog repozytorium jako projekt Gradle. Z terminala, po zainstalowaniu Gradle 9.6.0 i Android SDK, uruchom:

```bash
gradle --no-daemon assembleDebug
```

Wynik:

`app/build/outputs/apk/debug/app-debug.apk`

Repozytorium nie zawiera jeszcze Gradle Wrappera, dlatego lokalne budowanie wymaga zainstalowanego Gradle 9.6.0. Automatyczny workflow GitHub Actions konfiguruje odpowiednią wersję samodzielnie.

## Automatyczny build

Po pushu do gałęzi `main` GitHub Actions uruchamia workflow **Android debug APK**. Można go też uruchomić ręcznie w zakładce **Actions**.

Gotowy plik znajduje się w artifact:

`steam-android-hub-debug`

APK debug nie jest podpisanym wydaniem produkcyjnym. Do dystrybucji poza testami należy przygotować bezpieczny klucz podpisujący i osobny build release.

## Architektura

```
Steam Android Hub (Android 13+)
  ├─ sprawdza obecność Steam Link
  ├─ wyświetla instrukcję konfiguracji
  └─ uruchamia oficjalny Steam Link przez Android Intent
                                  │
                                  ▼
                         Steam Remote Play
                                  │
                  obraz / dźwięk / sterowanie
                                  │
                                  ▼
                       Steam działający na PC
                                  │
                                  ▼
                              Gra PC
```

Aplikacja nie próbuje sterować prywatnymi ekranami Steam Link i nie korzysta z nieudokumentowanych API Valve.

## Prywatność i bezpieczeństwo

- Aplikacja nie prosi o login ani hasło Steam.
- Nie deklaruje dostępu do mikrofonu, kamery, lokalizacji ani plików.
- Nie wysyła danych do własnego serwera.
- Sprawdza wyłącznie dostępność pakietu Steam Link i uruchamia oficjalną aplikację.
- Parowanie, transmisja i obsługa kontrolera pozostają po stronie Steam Link.

## Licencje i znaki towarowe

Kod tego repozytorium jest udostępniony na licencji MIT (plik `LICENSE`). Steam i Steam Link są znakami towarowymi Valve Corporation. Nazwy wykorzystano wyłącznie w celu opisania kompatybilności. Projekt nie używa oficjalnego logo i nie sugeruje, że jest produktem Valve.

## Plan rozwoju

- **v0.1.0:** panel Android, wykrywanie i uruchamianie Steam Link, instrukcja oraz automatyczny build APK.
- **v0.2.0:** lista zapisanych komputerów i test dostępności hosta w sieci lokalnej.
- **v0.3.0:** ocena możliwości integracji niezależnego klienta streamingowego (np. Sunshine/Moonlight), z poszanowaniem licencji i autorstwa tych projektów.
