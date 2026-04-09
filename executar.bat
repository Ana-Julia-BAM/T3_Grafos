@echo off
chcp 65001 > nul
cls

echo ========================================
echo Limpando arquivos antigos...
echo ========================================

REM Apagar todos os arquivos .class da pasta bin
if exist bin\ (
    echo Removendo classes compiladas...
    del /Q bin\*.class 2>nul
)

REM Apagar grafo_unifor.dot
if exist grafo_unifor.dot (
    echo Removendo grafo_unifor.dot...
    del grafo_unifor.dot
)

REM Apagar resultado.pdf
if exist resultado.pdf (
    echo Removendo resultado.pdf...
    del resultado.pdf
)

echo.
echo ========================================
echo Compilando projeto...
echo ========================================

REM Compilar todos os arquivos Java
javac -d bin -cp bin;lib src\*.java
if %ERRORLEVEL% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Executando programa...
echo ========================================

REM Executar a classe Main
java -cp bin;lib Main

echo.
echo ========================================
echo Gerando PDF do grafo...
echo ========================================

REM Gerar PDF a partir do arquivo DOT
if exist grafo_unifor.dot (
    dot -Tpdf grafo_unifor.dot -o resultado.pdf
    if %ERRORLEVEL% equ 0 (
        echo PDF gerado com sucesso: resultado.pdf
    ) else (
        echo Erro ao gerar PDF. Certifique-se de que Graphviz está instalado.
    )
) else (
    echo Arquivo grafo_unifor.dot nao encontrado.
)

echo.
echo ========================================
echo Processo finalizado!
echo ========================================
pause
