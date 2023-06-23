/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package edu.unicauca.apliweb.control;

import edu.unicauca.apliweb.persistence.entities.Videojuegos;
import edu.unicauca.apliweb.persistence.jpa.VideojuegosJpaController;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author edier
 */
@WebServlet("/")
public class ServletAppVideoGames extends HttpServlet {

    private VideojuegosJpaController videogamesJPA;
    private final static String PU = "edu.unicauca.apliweb_CrudVideoGame_war_1.0PU";

    @Override
    public void init() throws ServletException {
        super.init();
        //creamos una instancia de la clase EntityManagerFactory
        //esta clase se encarga de gestionar la construcción de entidades y
        //permite a los controladores JPA ejecutar las operaciones CRUD
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        //creamos una instancia del controldor JPA para la clase clients y le
        //pasamos el gestor de entidades
        videogamesJPA = new VideojuegosJpaController(emf);
        //esta parte es solamente para realizar la prueba:
        //listamos todos los clientes de la base de datos y los imprimimos en consola 
        List<Videojuegos> listaVideoGame = videogamesJPA.findVideojuegosEntities();
        //imprimimos los clientes en consola
        for (Videojuegos videogame : listaVideoGame) {
            System.out.println("Titulo " + videogame.getTitulo() + " precio " + videogame.getPrecio());
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getServletPath();
        //toma la acción solicitada desde la petición enviada al Servlet
        try {
            switch (action) {
                case "/new": //Muestra el formulario para crear un nuevo cliente
                    //showNewForm(request, response);
                    listVideoGames(request, response);
                    break;
                case "/insert": //ejecuta la creación de un nuevo cliente en la BD 
                    //insertClient(request, response);
                    listVideoGames(request, response);
                    break;
                case "/delete": //Ejecuta la eliminación de un cliente de la BD
                    deleteVideoGame(request, response);
                    //listVideoGames(request, response);
                    break;
                case "/edit": //Muestra el formulario para editar un cliente
                    //showEditForm(request, response);
                    listVideoGames(request, response);
                    break;
                case "/update": //Ejecuta la edición de un cliente de la BD
                    //updateClient(request, response);
                    listVideoGames(request, response);
                    break;
                default:
                    listVideoGames(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listVideoGames(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Videojuegos> listaVideoGames = videogamesJPA.findVideojuegosEntities();
        request.setAttribute("listVideoGame", listaVideoGames);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-videogames.jsp");
        dispatcher.forward(request, response);
    }

    //muestra el formulario para crear un nuevo usuario
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("client-form.jsp");
        dispatcher.forward(request, response);
    }

    //Elimina un cliente de la BD
    private void deleteVideoGame(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        //Recibe el ID del cliente que se espera eliminar de la BD
        int idVideojuegoPk = Integer.parseInt(request.getParameter("idVideojuegoPk"));
        try {
            //Elimina el cliente con el id indicado
            videogamesJPA.destroy(idVideojuegoPk);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h2>El videojuego se ha eliminado exitosamente</h2>");
            out.println("</body></html>");

        } catch (NonexistentEntityException ex) {

            Logger.getLogger(ServletAppVideoGames.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("list");
    }

    //muestra el formulario para editar un usuario
    /*private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        //toma el id del cliente a ser editaro
        int id = Integer.parseInt(request.getParameter("id"));
        //busca al cliente en la base de datos
        Clients existingClient = clientsJPA.findClients(id);
        RequestDispatcher dispatcher = null;
        if (existingClient != null) {
            //si lo encuentra lo envía al formulario
            dispatcher = request.getRequestDispatcher("client-form.jsp");
            request.setAttribute("cliente", existingClient);
        } else {
            //si no lo encuentra regresa a la página con la lista de los clientes
            dispatcher = request.getRequestDispatcher("list-clients.jsp");
        }
        dispatcher.forward(request, response);
    }*/
    //método para crear un cliente en la base de datos
    /*private void insertClient(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        //toma los datos del formulario de clientes
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        //crea un objeto de tipo Clients vacío y lo llena con los datos obtenidos 
        Clients cl = new Clients();
        cl.setName(name);
        cl.setEmail(email);
        cl.setCountry(country);
        //Crea el cliente utilizando el objeto controlador JPA
        clientsJPA.create(cl);
        //solicita al Servlet que muestre la página actualizada con la lista de clientes 
        response.sendRedirect("list");
    }*/
    //Método para editar un cliente
    /*private void updateClient(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        //toma los datos enviados por el formulario de clientes
        int id_client = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        //crea un objeto vacío y lo llena con los datos del cliente
        Clients cl = new Clients();
        cl.setId(id_client);
        cl.setName(name);
        cl.setEmail(email);
        cl.setCountry(country);
        try {
            //Edita el cliente en la BD
            clientsJPA.edit(cl);
        } catch (Exception ex) {

            Logger.getLogger(ServletAppClients.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("list");
    }*/
    /**
     * Procesa las peticiones HTTP <code>GET</code>.
     *
     * @param petición servlet request
     * @param respuesta servlet response
     * @throws ServletException excepción que se lanza si ocurre un error en el
     * servlet
     * @throws IOException excepción por si hay errores de tipo I/O
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Procesa las peticiones HTTP <code>POST</code>.
     *
     * @param petición servlet request
     * @param respuesta servlet response
     * @throws ServletException excepción que se lanza si ocurre un error en el
     * servlet
     * @throws IOException excepción por si hay errores de tipo I/O
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
