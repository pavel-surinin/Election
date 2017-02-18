var MenuCandidateComponent = React.createClass({
  render: function(){
    return (
      <div>
        BLABLABLA
      </div>
    );
  }
});

var MenuComponent = React.createClass({

  onKandidataiClick: function(){
    
  },

  render: function() {
    return (
      <div id="wrapper">

        <div id="sidebar-wrapper">
            <ul className="sidebar-nav">
                <div className="sidebar-brand">
                        RINKIMU SISTEMA
                </div>
                <li>
                    <a href="#" onClick={this.onKandidataiClick}>Kandidatai</a>
                </li>
                <li>
                    <a href="#">Partijos</a>
                </li>
                <li>
                    <a href="#">Apygardos</a>
                </li>
            </ul>
        </div>
        {this.props.children}

    </div>
    );
  }

});

window.MenuComponent = MenuComponent;
