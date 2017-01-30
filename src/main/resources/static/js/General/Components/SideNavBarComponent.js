var SideNavBarComponent = React.createClass({
	
	render: function(){
		return (

			  <div className="navbar-default sidebar" style={{ marginLeft: '-20px' }} role="navigation">
		        <div className="sidebar-nav navbar-collapse collapse">
		          <ul className="nav in" id="side-menu">

		            <li>
		              <a href="#/county" >
		              	Apygardos
		              </a>
		            </li>

		            <li>
		              <a href="#/district" >
		              	Apylinkės
		              </a>
		            </li>

		            <li>
		              <a href="#/representative" >
		              	Apylinkių atstovai
		              </a>
		            </li>
		            
		            <li>
		              <a href="#/candidate" >
		              	Kandidatai
		              </a>
		            </li>
		            
		            <li>
		              <a href="#/party"  >
		              	Partijos
		              </a>
		            </li>
		            
		          </ul>
		        </div>
		      </div>

		);
	}
});

window.SideNavBarComponent = SideNavBarComponent;