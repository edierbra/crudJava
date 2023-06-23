/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package edu.unicauca.apliweb.control;

import edu.unicauca.apliweb.persistence.entities.Categorias;
import edu.unicauca.apliweb.persistence.entities.Plataformas;
import edu.unicauca.apliweb.persistence.entities.Videojuegos;
import edu.unicauca.apliweb.persistence.jpa.CategoriasJpaController;
import edu.unicauca.apliweb.persistence.jpa.PlataformasJpaController;
import edu.unicauca.apliweb.persistence.jpa.VideojuegosJpaController;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import java.net.URLEncoder;
/**
 *
 * @author edier
 */
@WebServlet("/")
public class ServletAppVideoGames extends HttpServlet {

    private VideojuegosJpaController videogamesJPA;
    private CategoriasJpaController categoriasJPA;
    private PlataformasJpaController plataformasJPA;
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
        categoriasJPA = new CategoriasJpaController(emf);
        plataformasJPA = new PlataformasJpaController(emf);
        
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
                case "/new": //Muestra el formulario para crear un nuevo videojuego
                    showNewForm(request, response);                    
                    break;
                case "/insert": //ejecuta la creación de un nuevo videojuego en la BD 
                    insertVideoGame(request, response);                    
                    break;
                case "/delete": //Ejecuta la eliminación de un videojuego de la BD
                    deleteVideoGame(request, response);                    
                    break;
                case "/edit": //Muestra el formulario para editar un videojuego
                    showEditForm(request, response);                    
                    break;
                case "/update": //Ejecuta la edición de un videojuego de la BD
                    updateVideoGame(request, response);                   
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("videogame_form.jsp");
        List<Categorias> categorias = categoriasJPA.findCategoriasEntities();
        List<Plataformas> plataformas = plataformasJPA.findPlataformasEntities();
        request.setAttribute("listCategories", categorias);
        request.setAttribute("listPlatforms", plataformas);
        dispatcher.forward(request, response);
        
    }

    //Elimina un cliente de la BD
    private void deleteVideoGame(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException  {
        //Recibe el ID del cliente que se espera eliminar de la BD
        int idVideojuegoPk = Integer.parseInt(request.getParameter("idVideojuegoPk"));
        try {
            //Elimina el cliente con el id indicado
            videogamesJPA.destroy(idVideojuegoPk);

            // Mostrar mensaje de éxito en una ventana emergente
            String deleteMessage = "error_1";
            request.setAttribute("deleteMessage", deleteMessage);

        } catch (NonexistentEntityException ex) {

            Logger.getLogger(ServletAppVideoGames.class.getName()).log(Level.SEVERE, null, ex);
            String deleteMessage = "error_2";
            request.setAttribute("deleteMessage", deleteMessage);
        }
        request.getRequestDispatcher("list").forward(request, response);
    }
    
    //Método para editar un cliente
    private void updateVideoGame(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException, ServletException {
    // Toma los datos enviados por el formulario de videojuegos
    int idVideojuegoPk = Integer.parseInt(request.getParameter("id"));
    String titulo = request.getParameter("titulo");
    BigDecimal precio = new BigDecimal(request.getParameter("precio"));
    BigDecimal tamano = new BigDecimal(request.getParameter("tamano"));    
    int categoria = Integer.parseInt(request.getParameter("categoria"));
    int plataforma = Integer.parseInt(request.getParameter("plataforma"));
   

    // Crea un objeto vacío y lo llena con los datos del videojuego
    Videojuegos videojuego = new Videojuegos();
    videojuego.setIdVideojuegoPk(idVideojuegoPk);
    videojuego.setTitulo(titulo);
    videojuego.setPrecio(precio);
    videojuego.setTamano(tamano);
    
    Categorias cat = new Categorias(categoria);
    Plataformas plat = new Plataformas(plataforma);
    
    videojuego.setIdCategoriaFk(cat);
    videojuego.setIdPlataformaFk(plat);

    // Realiza las operaciones necesarias para actualizar el videojuego en la base de datos
    try {
        // Actualiza el videojuego en la BD
        videogamesJPA.edit(videojuego);
        // Mostrar mensaje de éxito en una ventana emergente
        String updateMessage = "error_1";
        request.setAttribute("updateMessage", updateMessage);
    } catch (Exception ex) {
        Logger.getLogger(ServletAppVideoGames.class.getName()).log(Level.SEVERE, null, ex);
        String updateMessage = "error_2";
        request.setAttribute("updateMessage", updateMessage);
    }

        request.getRequestDispatcher("list").forward(request, response);
}

    
    //muestra el formulario para editar un usuario
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        //toma el id del cliente a ser editaro
        int id = Integer.parseInt(request.getParameter("idVideojuegoPk"));
        //busca al cliente en la base de datos
        Videojuegos existingGame = videogamesJPA.findVideojuegos(id);
        List<Categorias> categorias = categoriasJPA.findCategoriasEntities();
        List<Plataformas> plataformas = plataformasJPA.findPlataformasEntities();
        RequestDispatcher dispatcher = null;
        if (existingGame != null) {
            //si lo encuentra lo envía al formulario
            dispatcher = request.getRequestDispatcher("videogame_form.jsp");
            request.setAttribute("videojuego", existingGame);
            request.setAttribute("listCategories", categorias);
            request.setAttribute("listPlatforms", plataformas);
        } else {
            //si no lo encuentra regresa a la página con la lista de los clientes
            dispatcher = request.getRequestDispatcher("list-videogames.jsp");
        }
        dispatcher.forward(request, response);
    }
    
    //método para crear un cliente en la base de datos
    private void insertVideoGame(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
       
        String titulo = request.getParameter("titulo");
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        BigDecimal tamano = new BigDecimal(request.getParameter("tamano"));    
        int categoria = Integer.parseInt(request.getParameter("categoria"));
        int plataforma = Integer.parseInt(request.getParameter("plataforma"));
        
        // Crea un objeto vacío y lo llena con los datos del videojuego
        Videojuegos videojuego = new Videojuegos();        
        videojuego.setTitulo(titulo);
        videojuego.setPrecio(precio);
        videojuego.setTamano(tamano);

        Categorias cat = new Categorias(categoria);
        Plataformas plat = new Plataformas(plataforma);

        videojuego.setIdCategoriaFk(cat);
        videojuego.setIdPlataformaFk(plat);
        
        // Realiza las operaciones necesarias para actualizar el videojuego en la base de datos
    try {
        // Actualiza el videojuego en la BD
        videogamesJPA.create(videojuego);
        // Mostrar mensaje de éxito en una ventana emergente
        String createMessage = "error_1";
        request.setAttribute("createMessage", createMessage);
    } catch (Exception ex) {
        Logger.getLogger(ServletAppVideoGames.class.getName()).log(Level.SEVERE, null, ex);
        String createMessage = "error_2";
        request.setAttribute("createMessage", createMessage);
    }

        request.getRequestDispatcher("list").forward(request, response);
    }
    
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
