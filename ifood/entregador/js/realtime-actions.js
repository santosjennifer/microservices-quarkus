function init() {
    connectWebSocket();
}

function connectWebSocket() {
    var socket = new WebSocket('ws://localhost:8085/localizacoes');
    socket.onopen = function(event) {
        console.log('Conectado ao WebSocket');
    };

    socket.onmessage = function(event) {
        console.log('Mensagem recebida: ' + event.data);
        atualizarLocalizacoes(event.data);
    };

    socket.onclose = function(event) {
        console.log('Desconectado do WebSocket');
    };
}

function atualizarLocalizacoes(mensagem) {
    var localizacoesTextArea = document.getElementById('localizacoes');
    localizacoesTextArea.value += mensagem + '\n';
}