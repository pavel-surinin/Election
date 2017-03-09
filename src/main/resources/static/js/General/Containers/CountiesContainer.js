/**
 * Created by nevyt on 2/21/2017.
 */
function getCounties(self){
    axios
        .get('/county')
        .then(function(response){
            self.setState({
                countyList: response.data,
                isLoading: false,
            });
        })

}
var CountiesContainer = React.createClass({
    getInitialState(){
        return {
            countyList: [],
            isLoading : true,
        };
    },
    componentWillMount(){
        getCounties(this);
    },
    render: function() {
      document.title='Apygardos Rinkimai 2017';
        if (this.state.isLoading) {
          return <div style={{marginTop : '3px', color : 'white', textAlign : 'center'}}><i className="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i></div>;
        } else {
            return (
                <CountiesListComponent
                    countyList={this.state.countyList}
                />
            );
        }
    }
});
window.CountiesContainer = CountiesContainer;
