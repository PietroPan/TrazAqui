package MVC.Model.Entregadores;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Common.*;
import Exceptions.*;

public class Entregadores implements InterfaceEntregadores, Serializable {
    private Map<String, InterfaceEntregador> entregadores;

    /**
     * Construtor vazio
     */
    public Entregadores() {
        entregadores=new HashMap<>();
    }

    /**
     * Construtor cópia
     * @param e entregadores a clonar para tomar o valor
     */
    public Entregadores(InterfaceEntregadores e) {
        entregadores=e.getEntregadores();
    }

    /**
     * Getter para o map de entregadores
     * @return map de entregadores clonados
     */
    @Override
    public Map<String, InterfaceEntregador> getEntregadores() {
       Map<String, InterfaceEntregador> res = new HashMap<>();
       for (Map.Entry<String, InterfaceEntregador> e : entregadores.entrySet()) {
           res.put(e.getKey(),e.getValue().clone());
        }
       return res;
    }

    /**
     * Setter para o map de entregadores
     * @param entregadores map de entregadores a clonar e colcar no seu lugar
     */
    @Override
    public void setEntregadores(Map<String, InterfaceEntregador> entregadores) {
        this.entregadores = new HashMap<>();
        for (Map.Entry<String, InterfaceEntregador> e : entregadores.entrySet()) {
            this.entregadores.put(e.getKey(),e.getValue().clone());
        }
    }

    /**
     * Getter para um entregador
     * @param e Código do entregador a procurar para get
     * @return Entregador pedido
     * @throws EntregadorInexistenteException Caso entregador pedido não exista
     */
    @Override
    public InterfaceEntregador getEntregador(String e) throws EntregadorInexistenteException {
        if (this.entregadores.containsKey(e))
            return this.entregadores.get(e).clone();
        else
            throw new EntregadorInexistenteException("InterfaceEntregador inexistente");
    }

    /**
     * Setter para o Entregador
     * @param s Código do entregador a inserir
     * @param e Entregador a inserir
     */
    @Override
    public void setEntregador(String s, InterfaceEntregador e) {
        this.entregadores.put(s,e.clone());
    }

    /**
     * Método toString
     * @return String com a informação relevante do map de entregadores
     */
    @Override
    public String toString() {
        return "Entregadores: " + entregadores.toString();
    }

    /**
     * Adicionar Encomenda a entregador
     * @param s Código do entregador ao qual adicionar a encomenda
     * @param e Encomenda a adicionar
     */
    @Override
    public void addEncomenda(String s, InterfaceEncomenda e) {
        entregadores.get(s).addEncomenda(e);
    }

    /**
     * Método que classifica um entregador
     * @param cod Código do entregador a classificar
     * @param clas Classificação a dar-lhe
     */
    @Override
    public void classificaUser(String cod,float clas){
        this.entregadores.get(cod).classifica(clas);
    }

    /**
     * Método responsável pela atualização do estado do programa
     * @param t Data a qual comparar para atualizar o estado
     * @return Mapa com a chave a ser o código de utilizador e uma lista das encomendas que foram entregues a este ate ao periodo de tempo t.
     */
    @Override
    public Map<String,List<InterfaceEncomenda>> atualizaEstado(LocalDateTime t) {
        Map<String,List<InterfaceEncomenda>> r = new HashMap<>();
        for (InterfaceEntregador e : this.entregadores.values()) {
            r.put(e.getCodigo(),e.atualizaEstado(t));
        }
        return r;
    }

    /**
     * Método de adição de pedido
     * @param enc Encomenda a adicionar
     * @param trans Transportadora a qual adicionar o pedido
     */
    @Override
    public void addPedido(InterfaceEncomenda enc,String trans){
        InterfaceTransportadora aux = ((InterfaceTransportadora)this.entregadores.get(trans));
        aux.addPedido(enc);
    }

    /**
     * Método de alteração de um pedido
     * @param enc Encomenda a alterar
     * @param trans Código da transportadora onde se encontra
     * @param stat estado no qual colocar a encomenda
     */
    @Override
    public void alteraPedido(InterfaceEncomenda enc,String trans,String stat){
        InterfaceTransportadora aux = ((InterfaceTransportadora)this.entregadores.get(trans));
        aux.alteraPedido(enc,stat);
    }

    /**
     * Método de adição de mensagem a um entregador
     * @param cod código do entregador
     * @param message mensagem a adicionar
     */
    @Override
    public void addMessage(String cod, String message) {
        this.entregadores.get(cod).addMessage(message);
    }

    /**
     * Método que atualiza o estado do entregador para randomEvents
     * @param cod código do entregador
     * @param enc código de encomenda
     */
    @Override
    public void atualizaAtual(String cod,InterfaceEncomenda enc){
        if (cod.contains("t")){
            InterfaceTransportadora trans = (InterfaceTransportadora)this.entregadores.get(cod);
            trans.atualizaAtual(enc);
        }
        else {
            InterfaceVoluntario vol = (InterfaceVoluntario)this.entregadores.get(cod);
            vol.atualizaAtual(enc);
        }
    }

    /**
     * Método que apaga todas as mensagens de um entregador
     * @param cod código do entregador ao qual apagar as mensagens
     */
    @Override
    public void resetMessages(String cod) {
        this.entregadores.get(cod).setMessages(new ArrayList<>());
    }

    /**
     * Setter de parametro a Entregar a um entregador
     * @param cod código do entregador
     * @param b novo valor de aEntregar
     */
    @Override
    public void setAEntregar(String cod,boolean b){
        this.entregadores.get(cod).setAEntregar(b);
    }

    /**
     * Getter para o parametro a Entregar de um entregador
     * @param cod código do entregador
     * @return o valor do parametro aEntregar no entregador cod
     */
    @Override
    public boolean isAEntregar(String cod){
        return this.entregadores.get(cod).isAEntregar();
    }

    /**
     * Método que verifica se existe espaço para transportar encomenda
     * @param trans código da transportadora
     * @return true se houver espaço e false otherwise
     */
    @Override
    public boolean hasRoom(String trans) {
        InterfaceTransportadora t = (InterfaceTransportadora) this.entregadores.get(trans);
        return t.hasRoom();
    }

    /**
     * Método que altera todos os pedidos atravês de uma condição
     * @param trans código da transportadora
     * @param stat estado ao qual comparar
     * @param statIf estado a colocar se a condição se verificar
     */
    @Override
    public void alteraTodosPedidosIf(String trans,String stat,String statIf){
        InterfaceTransportadora t = (InterfaceTransportadora) this.entregadores.get(trans);
        t.alteraTodosPedidosIf(stat,statIf);
    }

    /**
     * Método que retorna o estado de todas as encomendas das trasnportadora
     * @return estado de todas as encomendas das trasnportadora numa lista
     */
    @Override
    public List<String> getAllFree(){
        return this.entregadores.entrySet().stream().filter(i->i.getKey().contains("t")&&!(i.getValue().isAEntregar())).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * Método que verifica a existência de um pedido
     * @param trans transportadora onde verificar
     * @param enc código do pedido a procurar
     * @return true se existir, false otherwise
     */
    @Override
    public boolean existePedido(String trans,String enc){
        InterfaceTransportadora t = (InterfaceTransportadora)this.entregadores.get(trans);
        return t.existePedido(enc);
    }

    /**
     * Método responsável por eventos random que atrasam ou adiantam uma encomenda
     * @param t data à qual comparar
     * @return Map com as keys de códigos de um utilizador e como value uma lista de mensagens a dicionar-lhe
     */
    @Override
    public Map<String,List<String>> checkEvent (LocalDateTime t){
        Map<String,List<String>> r=new HashMap<>();
        for (Map.Entry<String,InterfaceEntregador> i : this.entregadores.entrySet()){
            if (i.getKey().contains("v")){
                InterfaceVoluntario vol = (InterfaceVoluntario)this.entregadores.get(i.getKey());
                Map.Entry<String,String> ret =vol.checkEvent(t);
                String key = ret.getKey();
                if (!key.equals("X")){
                    if(r.containsKey(key)){
                        List<String> aux=r.get(key);
                        aux.add(ret.getValue());
                        r.put(key,aux);
                    } else {
                        List<String>aux= new ArrayList<>();
                        aux.add(ret.getValue());
                        r.put(key,aux);
                    }
                }
            } else {
                InterfaceTransportadora trans = (InterfaceTransportadora)this.entregadores.get(i.getKey());
                Map<String,String> ret = trans.checkEvent(t);
                String key;
                for (Map.Entry<String,String> j : ret.entrySet()){
                    key=j.getKey();
                    if(r.containsKey(key)){
                        List<String> aux=r.get(key);
                        aux.add(j.getValue());
                        r.put(key,aux);
                    } else {
                        List<String> aux=new ArrayList<>();
                        aux.add(j.getValue());
                        r.put(key,aux);
                    }
                }
            }
        }
        return r;
    }

    /**
     * Verifica o tempo que falta para uma encomenda ser entregue
     * @param ent Codigo do entregador que tem a encomenda
     * @param enc Codigo da encomenda a verificar
     * @param l Tempo atual
     * @return mensagem com o resultado da comparação
     */
    @Override
    public String timeLeft(String ent,String enc,LocalDateTime l){
        if (ent.contains("v")){
            InterfaceVoluntario vol = (InterfaceVoluntario)this.entregadores.get(ent);
            return vol.timeLeft(enc,l);
        } else {
            InterfaceTransportadora trans = (InterfaceTransportadora)this.entregadores.get(ent);
            return trans.timeLeft(enc,l);
        }
    }

    /**
     * Rejeita pedidos a mais
     * @param enc Codigo de encomenda com pedidos a mais
     */
    @Override
    public void rejeitaPedidos(String enc){
        Map<String,InterfaceEntregador> aux = new HashMap<>();
        for (Map.Entry<String,InterfaceEntregador> i : this.entregadores.entrySet()){
            if (i.getKey().contains("t")){
                InterfaceTransportadora transAux = (InterfaceTransportadora)i.getValue();
                transAux.rejeitaPedidos(enc);
                aux.put(i.getKey(),transAux);
            }
        }
        this.entregadores.putAll(aux);
    }
}
