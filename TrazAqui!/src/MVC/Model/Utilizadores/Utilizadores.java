package MVC.Model.Utilizadores;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import Exceptions.*;
import Common.*;

public class Utilizadores implements InterfaceUtilizadores, Serializable {
    private Map<String, InterfaceUtilizador> users;

    /**
     * Construtor não vazio
     */
    public Utilizadores() {
        users=new HashMap<>();
    }

    /**
     * Construtor cópia
     * @param users utilizadores a copiar
     */
    public Utilizadores(InterfaceUtilizadores users) {
        this.users = users.getUsers();
    }

    /**
     * Getter para os users
     * @return parametro users clonado
     */
    @Override
    public Map<String, InterfaceUtilizador> getUsers() {
        Map<String, InterfaceUtilizador> res = new HashMap<>();
        for (Map.Entry<String, InterfaceUtilizador> e : users.entrySet()) {
            res.put(e.getKey(),e.getValue().clone());
        }
        return res;
    }

    /**
     * Setter para os utilizadores
     * @param users map copia de utilizadores
     */
    @Override
    public void setUsers(Map<String, InterfaceUtilizador> users) {
        this.users = new HashMap<>();
        for (Map.Entry<String, InterfaceUtilizador> e : users.entrySet()) {
            this.users.put(e.getKey(),e.getValue().clone());
        }
    }

    /**
     * Getter de um certo utilizador
     * @param cod código de utilizador
     * @return Utilizador a fazer get
     * @throws UtilizadorInexistenteException caso o útilizador não exista
     */
    @Override
    public InterfaceUtilizador getUser(String cod) throws UtilizadorInexistenteException {
        if (users.containsKey(cod))
            return users.get(cod).clone();
        else
            throw new UtilizadorInexistenteException("InterfaceUtilizador não registado");
    }

    /**
     * Adicionar um utilizador
     * @param u utilizador a adicionar
     */
    @Override
    public void addUser(InterfaceUtilizador u) {
        users.put(u.getCodigo(),u.clone());
    }

    /**
     * Método que trata do pagamento
     * @param e código do utilizador a pagar
     * @param money custo
     * @throws NotEnoughMoneyException caso o utilizador não tenho dinheiro suficiente
     */
    @Override
    public void pay(String e, double money) throws NotEnoughMoneyException {
        InterfaceUtilizador u =this.users.get(e);
        double after =u.getBalance()-money;
        if (after<0) {
            throw new NotEnoughMoneyException("O balanço da sua conta é insuficiente, a encomenda não pode ser feita");
        }
        u.setBalance(after);
    }

    /**
     * Adicionar mensagem ao utilizador
     * @param cod código
     * @param message mensagema  adicionar
     */
    @Override
    public void addMessageToUser(String cod, String message) {
        this.users.get(cod).addMessage(message);
    }

    /**
     * Método que dá reset das mensagens de um utilizador
     * @param cod código onde dar reset das mensagens
     */
    @Override
    public void resetMessages(String cod) {
        this.users.get(cod).setMessages(new ArrayList<>());
    }

    /**
     * Atualizar estado de um utilizador
     * @param m map de utilizadores para mensagens
     */
    @Override
    public void atualizaEstado(Map<String, List<String>> m) {
        for(Map.Entry<String,List<String>> mEntry : m.entrySet()) {
            InterfaceUtilizador u =this.users.get(mEntry.getKey());
            for (String mens : mEntry.getValue()) {
                u.addMessage(mens);
            }
        }
    }

    /**
     * Adicionar pedido
     * @param enc encomenda
     * @param trans código de transportadora
     */
    @Override
    public void addPedido(InterfaceEncomenda enc,String trans){
        InterfaceUtilizador aux = this.users.get(enc.getDestino());
        aux.addPedido(enc,trans);
    }

    /**
     * Alterar um pedido
     * @param enc encomenda
     * @param trans transportadora
     * @param stat estado a colocar
     */
    @Override
    public void alteraPedido(InterfaceEncomenda enc,String trans,String stat){
        InterfaceUtilizador aux = this.users.get(enc.getDestino());
        aux.alteraPedido(enc,trans,stat);
    }

    /**
     * Adição de encomenda entregue
     * @param uti utilizador onde adicionar
     * @param enc código de encomenda entregue
     */
    @Override
    public void addEntregue(String uti,String enc){
        this.users.get(uti).addEntregue(enc);
    }

    /**
     * Alterar todos os pedidos que verifiquem uma certa condição
     * @param trans transportadora
     * @param stat estado a verificar
     * @param statif estado a colocar no seu lugar
     */
    @Override
    public void alteraTodosPedidosIf(String trans,String stat,String statif){
        this.users=this.users.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, i->i.getValue().alteraTodosPedidosIf(trans,stat,statif)));
    }

    /**
     * Verificar o estado de um pedido
     * @param enc código de encomenda
     * @param trans código de transportadora
     * @param user código de utilizador
     * @return String do estado do pedido
     */
    @Override
    public String checkStatPedido(String enc,String trans,String user){
         return this.users.get(user).checkStatPedido(enc,trans);
    }

    /**
     * Atualização de pedidos
     * @param trans transportadora
     */
    @Override
    public void atualizaPedidos(List<String> trans){
        for (String i : trans){
            alteraTodosPedidosIf(i,"p","s");
        }
    }

    /**
     * Rejeita todos os pedidos a mais
     * @param enc encomenda com pedidos a mais
     */
     @Override
    public void rejeitaPedidos(String enc){
        for (Map.Entry<String,InterfaceUtilizador> i : this.users.entrySet()){
            InterfaceUtilizador aux = i.getValue();
            aux.rejeitaPedidos(enc);
            this.users.put(i.getKey(),aux);
         }
     }

    /**
     * Verifica se uma encomenda já foi aceite
     * @param usr Utilizador a quem pertence a encomenda
     * @param enc Encomenda a verificar
     * @return resultado da verificação
     */
     @Override
    public boolean isFree (String usr,String enc){
         return this.users.get(usr).isFree(enc);
     }
}
