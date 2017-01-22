var CountyDetailViewCandidatesRowComponent = React.createClass({
	render: function(){
		return (
			  <tr>
                <td>
                  {this.props.name}
                </td>
                <td>
                  {this.props.surname}
                </td>
                <td>
                  {this.props.birthDate}
                </td>
                <td>
                  {this.props.partijosPavadinimas}
                </td>
              </tr>
	    );
	}
});

window.CountyDetailViewCandidatesRowComponent = CountyDetailViewCandidatesRowComponent;