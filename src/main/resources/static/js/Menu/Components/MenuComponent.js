var MenuComponent = React.createClass({
  render: function() {
    return (
     <div className="nav-wrapper">
         <div className="container-fluid" style={{height:'225px',backgroundColor:'#b1b9ff'}}>
             <h2> SEIMO RINKIMAI 2099</h2>
         <div className="col-md-6 col-xs-12">
                 <div>Viso apylinkiu: X</div>
                 <div>Prabalsavo apylinkiu: Y</div>
                 <div className="progress">
                     <div className="progress-bar" role="progressbar" aria-valuenow="70"
                          aria-valuemin="0" aria-valuemax="100" style={{width:'70%'}}>
                         70%
                     </div>
                 </div>
             </div>
             <div className="col-md-6 col-xs-12">
                 <div>Viso rinkeju: A</div>
                 <div>Prabalsavo rinkeju: B</div>
                 <div className="progress">
                     <div className="progress-bar" role="progressbar" aria-valuenow="55"
                          aria-valuemin="0" aria-valuemax="100" style={{width:'55%'}}>
                         55%
                     </div>
                 </div>
             </div>
         </div>
             <nav
              className="navbar navbar-default">

                 <div className="container">
                     <div className="navbar-header">
                         <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-1">
                             <span className="sr-only">Toggle navigation</span>
                             <span className="icon-bar"></span>
                             <span className="icon-bar"></span>
                             <span className="icon-bar"></span>
                         </button>
                     </div>


                     <div className="collapse navbar-collapse" id="navbar-collapse-1">
                         <ul className="nav navbar-nav">
                             <li><a href="#">Pradinis</a></li>
                             <li><a href="#/candidate">Kandidatai</a></li>
                             <li><a href="#/parties">Partijos</a></li>
                             <li><a href="#/counties">Apygardos</a></li>
                             <li><a href="#/results">Rezultatai</a></li>
                             <li><a href="#/contacts">Kontaktai</a></li>
                         </ul>
                     </div>
                 </div>
             </nav>




             <div className="col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1 col-xs-10 col-xs-offset-1 children">
                 {this.props.children}
             </div>
         </div>

    );
  }
});

window.MenuComponent = MenuComponent;
